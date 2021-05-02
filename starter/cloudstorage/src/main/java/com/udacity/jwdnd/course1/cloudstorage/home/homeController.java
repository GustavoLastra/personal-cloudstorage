package com.udacity.jwdnd.course1.cloudstorage.home;

import com.udacity.jwdnd.course1.cloudstorage.auth.EncryptionService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    private FileService fileService;
    private UserService userService;
    private CredentialService credentialService;
    private NoteService noteService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, UserService userService, CredentialService credentialService, NoteService noteService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
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
                model.addAttribute("encryptionService", this.encryptionService);
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
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model, Authentication authentication, RedirectAttributes redirectAttrs) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            boolean IsSuccessful = this.fileService.deleteFile(fileId);
            addFlashAttributes(redirectAttrs, IsSuccessful, "File deleted", "files");

        }
        return "redirect:/home";
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttrs) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            boolean IsSuccessful = this.fileService.saveFile(file, user.getUserId());
            addFlashAttributes(redirectAttrs, IsSuccessful, "File uploaded", "files");
        }

        return "redirect:/home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication,
                             RedirectAttributes redirectAttrs) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user != null) {
            boolean IsSuccessful = this.noteService.deleteNote(noteId, user.getUserId());
            addFlashAttributes(redirectAttrs, IsSuccessful, "Note deleted", "notes");
        }
        return "redirect:/home";
    }

    @PostMapping("/credential")
    public String uploadCredential(@ModelAttribute("credential") Credential credential, Authentication authentication, RedirectAttributes redirectAttrs) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            credential.setUserId(user.getUserId());
            boolean IsSuccessful = false;
            if (credential.getCredentialId() == null)
                IsSuccessful = this.credentialService.saveCredential(credential);
            else
                IsSuccessful = this.credentialService.updateCredential(credential, user.getUserId());
            addFlashAttributes(redirectAttrs, IsSuccessful, "Credentials updated", "credentials");
        }
        return "redirect:/home";
    }

    @PostMapping("/note")
    public String uploadNote(@ModelAttribute("note") Note note, Authentication authentication, RedirectAttributes redirectAttrs) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user != null) {
            note.setUserId(user.getUserId());
            boolean IsSuccessful = this.noteService.saveNote(note);
            addFlashAttributes(redirectAttrs, IsSuccessful, "Notes updated", "notes");
        }

        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication,
                                   Model model, RedirectAttributes redirectAttrs) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user != null) {
            boolean IsSuccessful = this.credentialService.deleteCredential(credentialId, user.getUserId());
            addFlashAttributes(redirectAttrs, IsSuccessful, "Credential deleted", "credentials");
        }
        return "redirect:/home";
    }

    private void addFlashAttributes(RedirectAttributes redirectAttrs, boolean IsSuccessful, String successfulmessage, String activatedTab) {
        if (IsSuccessful) {
            redirectAttrs.addFlashAttribute("message", successfulmessage);
        } else {
            redirectAttrs.addFlashAttribute("message", "There was an error!");
        }
        redirectAttrs.addFlashAttribute("activatedTab", activatedTab);
    }
}
