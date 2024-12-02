package by.lupach.patientaccountingsystemrestapiclient.converters;

import by.lupach.patientaccountingsystemrestapiclient.dto.Gender;
import by.lupach.patientaccountingsystemrestapiclient.dto.Page;
import by.lupach.patientaccountingsystemrestapiclient.dto.PatientDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ObjectToPageConverter {
    public Page<Object> convert(Object source) {
        Map patientsPageMap = (Map) source;
        List<Object> contentObject = (List<Object>) patientsPageMap.get("content");
        Integer pageSize = (Integer) patientsPageMap.get("numberOfElements");
        Integer totalPages = (Integer) patientsPageMap.get("totalPages");
        Integer number = (Integer) patientsPageMap.get("number"); // currentPage

//        Page<Object> patients = (Page<Object>) patientObject;
//        List<Object> patientDTOList = new ArrayList<>();
//        for (Object object : patientObject) {
//            PatientDTO patientDTO = convertObjectToPatient(object);
//            patientDTOList.add(patientDTO);
//        }

        Page page = new Page(contentObject, pageSize, totalPages, number);
        return page;
    }

//    private PatientDTO convertObjectToPatient(Object patientObject) {
//        Map patientMap = new LinkedHashMap((Map) patientObject);
//        Integer id = (Integer) patientMap.get("id");
//        String name = (String) patientMap.get("name");
//        String genderString = (String) patientMap.get("gender");
//        Gender gender = genderString != null ? Gender.valueOf(genderString.toUpperCase()) : null;
//        Integer age = (Integer) patientMap.get("age");
//        Integer height = (Integer) patientMap.get("height");
//        String hairColor = (String) patientMap.get("hairColor");
//        String distinctiveCharacteristics = (String) patientMap.get("distinctiveCharacteristics");
//
//        PatientDTO patient = PatientDTO.builder()
//                .id(id)
//                .name(name)
//                .gender(gender)
//                .age(age)
//                .height(height)
//                .hairColor(hairColor)
//                .distinctiveCharacteristics(distinctiveCharacteristics)
//                .build();
//
//        return patient;
//
//    }
}
