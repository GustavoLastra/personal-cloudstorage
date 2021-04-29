package com.udacity.jwdnd.course1.cloudstorage.homeAPI;

import com.udacity.jwdnd.course1.cloudstorage.credential.Credential;
import com.udacity.jwdnd.course1.cloudstorage.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.file.File;
import com.udacity.jwdnd.course1.cloudstorage.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.note.Note;
import com.udacity.jwdnd.course1.cloudstorage.note.NoteService;
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
    private NoteService noteService;

    public HomeController(FileService fileService, UserService userService, CredentialService credentialService, NoteService noteService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
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
                model.addAttribute("noteList", this.noteService.getNoteList(user.getUserId()));
            } catch (Exception error) {
                this.logger.error(error.getMessage());
            }
        }
        return "home";
    }

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") Integer fileId, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user != null) {
            File file = this.fileService.getFile(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getFileData());
        }

        return ResponseEntity.badRequest().body("Authentication failure");
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            if (this.fileService.deleteFile(fileId)) {
                model.addAttribute("actionSuccess", true);
            } else {
                model.addAttribute("actionFail", true);
            }
        }
        return "redirect:/home";
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            Integer fileId = this.fileService.saveFile(file, user.getUserId());
        }

        return "redirect:/home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication,
                             Model model) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user != null) {
            this.noteService.deleteNote(noteId, user.getUserId());
        }
        return "redirect:/home";
    }

    @PostMapping("/credential")
    public String uploadCredential(@ModelAttribute("credential") Credential credential, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user != null) {
            credential.setUserId(user.getUserId());
            Integer credentialId = this.credentialService.saveCredential(credential);
        }

        return "redirect:/home";
    }

    @PostMapping("/note")
    public String uploadNote(@ModelAttribute("note") Note note, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            note.setUserId(user.getUserId());
            Integer noteId = this.noteService.saveNote(note);
        }

        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication,
                                   Model model) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user != null) {
            this.credentialService.deleteCredential(credentialId, user.getUserId());
        }
        return "redirect:/home";
    }
}
