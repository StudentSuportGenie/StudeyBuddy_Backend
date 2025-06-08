package com.example.studyBuddy.Services;

import com.example.studyBuddy.DTO.studentdetailsDTO;
import com.example.studyBuddy.Emailhandel.EmailDTO;
import com.example.studyBuddy.Emailhandel.EmailServices;
import com.example.studyBuddy.Models.DateReminder;
import com.example.studyBuddy.Models.ScheduleOfTime;
import com.example.studyBuddy.Models.StudentDetails;
import com.example.studyBuddy.Repo.DataReminderRepo;
import com.example.studyBuddy.Repo.StudentDetailsRepo;
import com.example.studyBuddy.Repo.StudentTimescheduleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentDetailsServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private StudentDetailsRepo studentDetailsRepo;

    @Autowired
    private StudentTimescheduleRepo studentTimescheduleRepo;

    @Autowired
    private DataReminderRepo dataReminderRepo;

    public studentdetailsDTO AddStudentDetails(studentdetailsDTO studentdetailsDTO) {

       StudentDetails registedstudent = studentDetailsRepo.findByStudentEmail(studentdetailsDTO.getStudentEmail()).orElse(null);

       if (registedstudent != null) {
           throw new IllegalStateException("A student with this email already exists: " + studentdetailsDTO.getStudentEmail());
       }

        StudentDetails studentDetails = modelMapper.map(studentdetailsDTO, StudentDetails.class);

        studentDetailsRepo.save(studentDetails);

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setReceiver(studentDetails.getStudentEmail());
        emailDTO.setSubject("Welcome to Study Buddy");
        emailDTO.setBody("Dear " + studentdetailsDTO.getStudentEmail() + ",\n\n" +
                "Thank you for registering with StudyBuddy.\n\n" +
                "Best regards,\nStudyBuddy Team");

        // Send welcome email
        emailServices.sendEmail(emailDTO);

        return studentdetailsDTO;
    }

    public studentdetailsDTO getstudentdetails(String studentEmail) {
        StudentDetails studentDetails = studentDetailsRepo.findByStudentEmail(studentEmail).orElse(null);
        if (studentDetails == null) {
            throw new IllegalStateException("Student with this email does not exist: " + studentEmail);
        }
        return modelMapper.map(studentDetails, studentdetailsDTO.class);
    }

    public studentdetailsDTO updatestudentdetails(studentdetailsDTO studentDetailsDTO,String studentEmail) {
        StudentDetails findStudentdetails = studentDetailsRepo.findByStudentEmail(studentEmail).orElse(null);
        if (findStudentdetails == null) {
            throw new IllegalStateException("Student with this email does not exist: " + studentEmail);
        }

        findStudentdetails.setStudentBirthday(studentDetailsDTO.getStudentBirthday());
        findStudentdetails.setStudentProfile(studentDetailsDTO.getStudentProfile());
        findStudentdetails.setStudentGender(StudentDetails.Gender.valueOf(studentDetailsDTO.getStudentGender()));

        StudentDetails Updatestudent = studentDetailsRepo.save(findStudentdetails);

        return modelMapper.map(Updatestudent, studentdetailsDTO.class);
    }

    public String deletestudentdetails(int studentId) {
     StudentDetails studentDetails = studentDetailsRepo.findById(studentId).orElse(null);
     if (studentDetails == null) {
         throw new IllegalStateException("Student with this id does not exist: " + studentId);
     }
     EmailDTO emailDTO = new EmailDTO();
     emailDTO.setReceiver(studentDetails.getStudentEmail());
     emailDTO.setSubject("Account Deletion ");
     emailDTO.setBody("Your Account Remove From Study Buddy");

     emailServices.sendEmail(emailDTO);

     studentTimescheduleRepo.deleteById(studentId);
     dataReminderRepo.deleteById(studentId);
     studentDetailsRepo.delete(studentDetails);
     return studentDetails.getStudentEmail();
    }

    public List<studentdetailsDTO> getallstudentdetails() {
        List<StudentDetails> studentDetailsList = studentDetailsRepo.findAll();

        return studentDetailsList.stream().map(student ->
                modelMapper.map(student, studentdetailsDTO.class))
                .collect(Collectors.toList());

    }
}
