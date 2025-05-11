package com.example.studyBuddy.Services;

import com.example.studyBuddy.DTO.studentdetailsDTO;
import com.example.studyBuddy.Emailhandel.EmailDTO;
import com.example.studyBuddy.Emailhandel.EmailServices;
import com.example.studyBuddy.Models.StudentDetails;
import com.example.studyBuddy.Repo.StudentDetailsRepo;
import com.example.studyBuddy.Repo.StudentTimescheduleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private StudentDetailsRepo studentDetailsRepo;

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

}
