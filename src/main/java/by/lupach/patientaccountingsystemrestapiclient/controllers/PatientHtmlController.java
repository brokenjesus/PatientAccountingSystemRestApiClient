package by.lupach.patientaccountingsystemrestapiclient.controllers;

import by.lupach.patientaccountingsystemrestapiclient.converters.ObjectToListConverter;
import by.lupach.patientaccountingsystemrestapiclient.converters.ObjectToPageConverter;
import by.lupach.patientaccountingsystemrestapiclient.dto.Page;
import by.lupach.patientaccountingsystemrestapiclient.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/patients")
public class PatientHtmlController {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "http://localhost:8080/api/v1/patients";

    @PostMapping("/create-patient")
    public String createPatient(@ModelAttribute PatientDTO patient) {
        restTemplate.postForObject(apiUrl, patient, Void.class);
        return "redirect:/patients";
    }

    @GetMapping
    public String patients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        // Формируем запрос с параметрами пагинации
        String url = apiUrl + "?page=" + page + "&size=" + size;
        ObjectToPageConverter converter = new ObjectToPageConverter();
        Object patientsPageObject = restTemplate.getForObject(url, Object.class);
        Page<Object> patientsPage = converter.convert(patientsPageObject);

        model.addAttribute("patientsPage", patientsPage);
        return "patients";
    }

    @GetMapping("/edit-patient/{id}")
    public String editPatient(@PathVariable("id") int id, Model model) {
        PatientDTO patient = restTemplate.getForObject(apiUrl + "/" + id, PatientDTO.class);
        model.addAttribute("patient", patient);
        return "edit_patient";
    }

    @PostMapping("/edit-patient/{id}")
    public String updatePatient(@PathVariable("id") int id, @ModelAttribute PatientDTO patient) {
        restTemplate.put(apiUrl + "/" + id, patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable("id") Integer id) {
        restTemplate.delete(apiUrl + "/" + id);
        return "redirect:/patients";
    }

    @GetMapping("/search")
    public String searchPatients(@RequestParam String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        String url = apiUrl + "/search?name=" + name + "&page=" + page + "&size=" + size;
        ObjectToPageConverter converter = new ObjectToPageConverter();
        Object patientsPageObject = restTemplate.getForObject(url, Object.class);
        Page<Object> patientsPage = converter.convert(patientsPageObject);

        model.addAttribute("patientsPage", patientsPage);
        return "patients";
    }

    @GetMapping("/custom-query/get-patient-ward")
    public String getPatientWardAndPhoneByName(@RequestParam String name, Model model) {
        String url = apiUrl + "/custom-query/get-patient-ward?name=" + name;
        ObjectToListConverter converter = new ObjectToListConverter();
        List<Object> result = (List<Object>) restTemplate.getForObject(url, Object.class);
        model.addAttribute("customQueryResult", result);
        return "patient_custom_search_result";
    }

    @GetMapping("/custom-query/get-patients-by-date")
    public String getPatientsByDate(@RequestParam String date, Model model) {
        String url = apiUrl + "/custom-query/get-patients-by-date?date=" + date;
        ObjectToListConverter converter = new ObjectToListConverter();
        List<Object> result = (List<Object>) restTemplate.getForObject(url, Object.class);
        model.addAttribute("customQueryResult", result);
        return "patient_custom_search_result";
    }


    @GetMapping("/custom-query/get-female-by-age")
    public String getFemalePatientsByAge(@RequestParam Integer age, Model model) {
        String url = apiUrl + "/custom-query/get-female-by-age?age=" + age;
        ObjectToListConverter converter = new ObjectToListConverter();
        List<Object> result = (List<Object>) restTemplate.getForObject(url, Object.class);
        model.addAttribute("customQueryResult", result);
        return "patient_custom_search_result";
    }


}
