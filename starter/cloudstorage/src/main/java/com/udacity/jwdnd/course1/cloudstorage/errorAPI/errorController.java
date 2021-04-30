package com.udacity.jwdnd.course1.cloudstorage.errorAPI;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class errorController {

    @Controller
    public class MyErrorController implements ErrorController {

        @RequestMapping("/error")
        public String handleError() {
            return "error";
        }

        @Override
        public String getErrorPath() {
            return null;
        }
    }
}
