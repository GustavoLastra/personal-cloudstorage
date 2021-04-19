package com.udacity.jwdnd.course1.cloudstorage.home;

import com.udacity.jwdnd.course1.cloudstorage.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);
    private FileService fileService;
    private UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping()
    public String homeView() {
        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        Integer fileId = this.fileService.saveFile(file, userId);
        logger.info(fileId.toString());
        return "home";
    }
}
