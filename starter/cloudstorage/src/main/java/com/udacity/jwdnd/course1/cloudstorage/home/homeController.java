package com.udacity.jwdnd.course1.cloudstorage.home;

import com.udacity.jwdnd.course1.cloudstorage.credential.Credential;
import com.udacity.jwdnd.course1.cloudstorage.credential.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.file.File;
import com.udacity.jwdnd.course1.cloudstorage.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.user.User;
import com.udacity.jwdnd.course1.cloudstorage.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    private FileService fileService;
    private UserService userService;
    private CredentialService credentialService;

    public HomeController(FileService fileService, UserService userService, CredentialService credentialService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String homeView(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            try {
                model.addAttribute("fileList", this.fileService.getFileList(user.getUserId()));
                model.addAttribute("credentialList", this.credentialService.getCredentialList(user.getUserId()));
            } catch (Exception error) {
                this.logger.error(error.getMessage());
            }

            //this.logger.info(model.getAttribute("fileList").toString());
            //this.logger.info(model.getAttribute("credentialList").toString());
        }
        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            Integer fileId = this.fileService.saveFile(file, user.getUserId());
        }

        return "redirect:/home";
    }

    @PostMapping("/credential")
    public String uploadFile(@ModelAttribute("newCredential") CredentialForm credentialForm, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            Integer credentialId = this.credentialService.saveCredential( credentialForm, user.getUserId());
        }

        return "redirect:/home";
    }
}
