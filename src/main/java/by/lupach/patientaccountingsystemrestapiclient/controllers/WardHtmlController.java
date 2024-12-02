package by.lupach.patientaccountingsystemrestapiclient.controllers;

import by.lupach.patientaccountingsystemrestapiclient.converters.ObjectToPageConverter;
import by.lupach.patientaccountingsystemrestapiclient.dto.Page;
import by.lupach.patientaccountingsystemrestapiclient.dto.WardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/wards")
public class WardHtmlController {

    @Autowired
    private RestTemplate restTemplate;

    private final String wardApiUrl = "http://localhost:8080/api/v1/wards";

    // Метод для отображения списка всех палат
    @GetMapping
    public String listWards(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size) {
        // Отправка запроса с параметрами страницы и размера
        String url = wardApiUrl + "?page=" + page + "&size=" + size;
        ObjectToPageConverter converter = new ObjectToPageConverter();
        Object wardsPageObject = restTemplate.getForObject(url, Object.class);
        Page<Object> wardsPage = converter.convert(wardsPageObject);

        model.addAttribute("wardsPage", wardsPage);
        return "wards";
    }


    // Метод для поиска по номеру палаты
    @GetMapping("/search")
    public String searchByNumber(@RequestParam("number") String number, Model model,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        String url = wardApiUrl + "/search?number=" + number + "&page=" + page + "&size=" + size;
        ObjectToPageConverter converter = new ObjectToPageConverter();
        Object wardsPageObject = restTemplate.getForObject(url, Object.class);
        Page<Object> wardsPage = converter.convert(wardsPageObject);

        model.addAttribute("wardsPage", wardsPage);
        return "wards";
    }

    // Метод для отображения формы создания новой палаты
    @GetMapping("/create-ward")
    public String createWardForm(Model model) {
        model.addAttribute("ward", new WardDTO());
        return "create_ward";
    }

    // Метод для создания новой палаты
    @PostMapping("/create-ward")
    public String createWard(@ModelAttribute WardDTO wardDTO) {
        restTemplate.postForObject(wardApiUrl, wardDTO, WardDTO.class);
        return "redirect:/wards";
    }

    // Метод для отображения формы редактирования палаты
    @GetMapping("/edit-ward/{id}")
    public String editWardForm(@PathVariable("id") Integer id, Model model) {
        WardDTO ward = restTemplate.getForObject(wardApiUrl + "/" + id, WardDTO.class);
        if (ward != null) {
            model.addAttribute("ward", ward);
            return "edit_ward";
        } else {
            return "redirect:/wards"; // если палата не найдена, редирект на список
        }
    }

    // Метод для обновления палаты
    @PostMapping("/edit-ward/{id}")
    public String updateWard(@PathVariable("id") Integer id, @ModelAttribute WardDTO wardDTO) {
        restTemplate.put(wardApiUrl + "/" + id, wardDTO);
        return "redirect:/wards";
    }

    // Метод для удаления палаты
    @GetMapping("/delete-ward/{id}")
    public String deleteWard(@PathVariable("id") Integer id) {
        restTemplate.delete(wardApiUrl + "/" + id);
        return "redirect:/wards";
    }
}
