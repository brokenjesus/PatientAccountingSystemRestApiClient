package by.lupach.patientaccountingsystemrestapiclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexHtmlPageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
