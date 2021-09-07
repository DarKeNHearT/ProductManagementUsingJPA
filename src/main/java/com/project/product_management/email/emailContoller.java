package com.project.product_management.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("send/mail/toUsers/")
public class emailContoller {
    @Autowired
    private EmailService emailService;

    @PostMapping(value="/textmail")
    public String sendTextMail(@ModelAttribute("emails") EmailTemplate emailTemplate )throws Exception{
        try {
            emailService.sendTextEmail(emailTemplate);
        }
        catch (Exception e) {
            return "redirect:/EmailFail";
        }
        return "redirect:/Email";
    }

    @PostMapping(value = "/filemail",consumes = "multipart/form-data")
    public String sendFileMail(@ModelAttribute("emails") EmailTemplate emailTemplate, @RequestParam("file")MultipartFile file){
        try{
            emailService.sendEmailWithAttachment(file,emailTemplate);
        }
        catch (Exception e){
            return "redirect:/EmailFail";
        }
        return "redirect:/Email";
    }
}
