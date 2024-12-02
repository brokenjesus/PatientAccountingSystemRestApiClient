package by.lupach.patientaccountingsystemrestapiclient.controllers;

import by.lupach.patientaccountingsystemrestapiclient.dto.AdmissionDischargeHistoryDTO;
import by.lupach.patientaccountingsystemrestapiclient.dto.PatientDTO;
import by.lupach.patientaccountingsystemrestapiclient.dto.Reason;
import by.lupach.patientaccountingsystemrestapiclient.dto.WardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patients/patient-history")
public class PatientHistoryHtmlController {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "http://localhost:8080/api/v1/patient-history";

    @GetMapping("/{id}")
    public String getPatientHistory(@PathVariable("id") int patientId, Model model) {
        PatientDTO patient = restTemplate.getForObject(apiUrl + "/" + patientId, PatientDTO.class);
        List<AdmissionDischargeHistoryDTO> history = List.of(restTemplate.getForObject(apiUrl + "/" + patientId + "/history", AdmissionDischargeHistoryDTO[].class));
        List<WardDTO> wards = List.of(restTemplate.getForObject(apiUrl + "/wards", WardDTO[].class));

        model.addAttribute("patient", patient);
        model.addAttribute("wards", wards);
        model.addAttribute("admissionDischargeHistory", history);

        boolean readyToAdmit = history.isEmpty() || history.get(history.size() - 1).getReason() == Reason.DISCHARGE;
        model.addAttribute("readyToAdmit", readyToAdmit);

        return "patient_history";
    }

    @PostMapping("/{id}/add_history")
    public String saveHistory(@PathVariable("id") int patientId,
                              @ModelAttribute AdmissionDischargeHistoryDTO admissionDischargeHistory,
                              @RequestParam(required = false) Integer wardId) {
        String url = apiUrl + "/"+patientId+"/" + wardId;
        PatientDTO patient = restTemplate.getForObject(apiUrl + "/" + patientId, PatientDTO.class);
        admissionDischargeHistory.setPatient(patient);
        restTemplate.postForObject(url, admissionDischargeHistory, Void.class);
        return "redirect:/patients/patient-history/" + patientId;
    }

    @GetMapping("/{patientId}/edit/{historyId}")
    public String editPatientHistory(@PathVariable("patientId") int patientId, @PathVariable("historyId") int historyId, Model model) {
        AdmissionDischargeHistoryDTO history = restTemplate.getForObject(apiUrl + "/" + patientId + "/edit/" + historyId, AdmissionDischargeHistoryDTO.class);
        PatientDTO patient = restTemplate.getForObject(apiUrl + "/" + patientId, PatientDTO.class);
        model.addAttribute("history", history);
        model.addAttribute("patient", patient);
        return "edit_patient_history";
    }

    @PostMapping("/{patientId}/edit/{historyId}")
    public String updateHistory(@PathVariable("patientId") int patientId, @PathVariable("historyId") int historyId,
                                @ModelAttribute AdmissionDischargeHistoryDTO history) {
        String url = apiUrl + "/" + patientId + "/edit/" + historyId;
        PatientDTO patient = restTemplate.getForObject(apiUrl + "/" + patientId, PatientDTO.class);
        history.setPatient(patient);
        restTemplate.put(url, history);
        return "redirect:/patients/patient-history/" + patientId;
    }


    @GetMapping("/{patientId}/delete/{historyId}")
    public String delete(@PathVariable("patientId") int patientId, @PathVariable("historyId") int historyId) {
        restTemplate.delete(apiUrl + "/" + patientId + "/delete/" + historyId);
        return "redirect:/patients/patient-history/" + patientId;
    }
}
