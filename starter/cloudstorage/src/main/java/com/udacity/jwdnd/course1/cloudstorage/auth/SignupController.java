package com.udacity.jwdnd.course1.cloudstorage.auth;

import com.udacity.jwdnd.course1.cloudstorage.user.User;
import com.udacity.jwdnd.course1.cloudstorage.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, RedirectAttributes redirectAttrs, Model model) {
        String signupError = null;
        boolean signupSuccess = false;

        if (!userService.isUsernameAvailable(user.getUserName())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttrs.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
