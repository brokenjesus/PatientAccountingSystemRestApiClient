package by.lupach.patientaccountingsystemrestapiclient.controllers;

import by.lupach.patientaccountingsystemrestapiclient.converters.ObjectToPageConverter;
import by.lupach.patientaccountingsystemrestapiclient.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/transfers")
public class TransferHtmlController {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "http://localhost:8080/api/v1/transfers";

    // Получить все переводы с пагинацией
    @GetMapping()
    public String listTransfers(@RequestParam(defaultValue = "0") int page, Model model,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        String url = apiUrl + "?page=" + page + "&size=" + size;
        ObjectToPageConverter converter = new ObjectToPageConverter();
        Object transferPageObject = restTemplate.getForObject(url, Object.class);
        Page<Object> transferPage = converter.convert(transferPageObject);

        List<WardDTO> wards = (List<WardDTO>) restTemplate.getForObject("http://localhost:8080/api/v1/wards/available", Object.class);
        List<PatientDTO> patients = (List<PatientDTO>) restTemplate.getForObject("http://localhost:8080/api/v1/patients/admitted", Object.class);

        model.addAttribute("transferPage", transferPage);
        model.addAttribute("wards", wards);
        model.addAttribute("patients", patients);
        return "transfers";
    }

    // Поиск переводов по номеру палаты или имени пациента
    @GetMapping("/search")
    public String searchTransfers(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "wardNumber", required = false) String wardNumber,
            @RequestParam(value = "patientName", required = false) String patientName,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        String url = apiUrl + "/search?wardNumber=" + wardNumber + "&patientName=" + patientName +"&page=" + page + "&size=" + size;
        ObjectToPageConverter converter = new ObjectToPageConverter();
        Object transferPageObject = restTemplate.getForObject(url, Object.class);
        Page<Object> transferPage = converter.convert(transferPageObject);
        model.addAttribute("transferPage", transferPage);
        return "transfers";
    }

    // Добавить перевод
    @PostMapping("/create-transfer")
    public String createTransfer(@ModelAttribute TransferDTO transferDTO) {
        restTemplate.postForObject(apiUrl + "/create-transfer", transferDTO, Void.class);
        return "redirect:/transfers";
    }

    // Удалить перевод
    @GetMapping("/delete-transfer/{id}")
    public String deleteTransfer(@PathVariable("id") Integer id) {
        restTemplate.delete(apiUrl + "/delete-transfer/" + id);
        return "redirect:/transfers";
    }

    // Редактировать перевод
    @GetMapping("/edit-transfer/{id}")
    public String editTransfer(@PathVariable("id") Integer id, Model model) {
        String url = apiUrl + "/" + id;
        TransferDTO transfer = restTemplate.getForObject(url, TransferDTO.class);
        List<WardDTO> wards = (List<WardDTO>) restTemplate.getForObject("http://localhost:8080/api/v1/wards/available", Object.class);

        model.addAttribute("transfer", transfer);
        model.addAttribute("wards", wards);
        return "edit_transfer";
    }

    @PostMapping("/edit-transfer/{id}")
    public String updateTransfer(@PathVariable("id") Integer id, @ModelAttribute TransferDTO transferDTO) {
        restTemplate.put(apiUrl + "/" + id, transferDTO);

        return "redirect:/transfers";
    }
}
