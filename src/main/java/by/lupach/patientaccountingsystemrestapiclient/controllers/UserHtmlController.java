package by.lupach.patientaccountingsystemrestapiclient.controllers;

import by.lupach.patientaccountingsystemrestapiclient.dto.UserDTO;
import by.lupach.patientaccountingsystemrestapiclient.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserHtmlController {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "http://localhost:8080/api/v1/users";
    @Autowired
    private UserService userService;

    // Переход на страницу регистрации
    @GetMapping("admin/signup")
    public String signup() {
        return "signup";
    }

    // Обработка регистрации нового пользователя
    @PostMapping("admin/signup")
    public String processSignup(@ModelAttribute("newUser") UserDTO user) {
        restTemplate.postForObject(apiUrl + "/signup", user, Void.class);
        return "redirect:/admin/manage-users";
    }

    // Переход на страницу входа
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login-processing")
    public String processLogin(String username, String password) {
        UserDTO user = userService.loadUserByUsername(username);
        return "redirect:/";
    }


    // Поиск пользователя по имени
    @GetMapping("admin/manage-users/search")
    public String searchUsers(@RequestParam String username, Model model) {
        UserDTO user = restTemplate.getForObject(apiUrl + "/search?username=" + username, UserDTO.class);
        model.addAttribute("users", user);
        return "manage_users";
    }

    // Отображение всех пользователей с пагинацией
    @GetMapping("admin/manage-users")
    public String manageUsers(@RequestParam(defaultValue = "0") int page, Model model) {
        UserDTO[] users = restTemplate.getForObject(apiUrl + "/manage?page=" + page, UserDTO[].class);
        model.addAttribute("users", users);
        return "manage_users";
    }

    // Удаление пользователя
    @GetMapping("admin/manage-users/delete")
    public String deleteUser(@RequestParam int id) {
        restTemplate.delete(apiUrl + "/delete/" + id);
        return "redirect:/admin/manage-users";
    }

    // Переход на страницу редактирования профиля пользователя
    @GetMapping("admin/manage-users/edit")
    public String editUser(@RequestParam String username, Model model) {
        UserDTO user = restTemplate.getForObject(apiUrl + "/search?username=" + username, UserDTO.class);
        model.addAttribute("userToEdit", user);
        return "edit_profile";
    }

    // Обработка редактирования пользователя
    @PostMapping("admin/manage-users/edit")
    public String processEditUser(@ModelAttribute("userToEdit") UserDTO user) {
        restTemplate.postForObject(apiUrl + "/edit", user, Void.class);
        return "redirect:/admin/manage-users";
    }
}
