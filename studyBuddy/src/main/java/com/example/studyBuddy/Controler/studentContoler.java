package com.example.studyBuddy.Controler;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API/V1/Auth")
public class studentContoler {

    @GetMapping("/getdata")
    public String getdata() {
        return "Hello World";
    }
}
