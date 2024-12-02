package by.lupach.patientaccountingsystemrestapiclient.controllers;

import by.lupach.patientaccountingsystemrestapiclient.dto.UserDTO;
import by.lupach.patientaccountingsystemrestapiclient.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    private final String userApiUrl = "http://localhost:8080/api/v1/users/current";


    @ModelAttribute("user")
    public void addUserToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails user = ((UserDetails) authentication.getPrincipal());
            UserDTO currentUser = userService.loadUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
            System.out.println(currentUser.getUsername());
            model.addAttribute("currentUser", currentUser);
        }
    }
}
