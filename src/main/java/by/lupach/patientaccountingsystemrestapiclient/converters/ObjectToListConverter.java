package by.lupach.patientaccountingsystemrestapiclient.converters;

import by.lupach.patientaccountingsystemrestapiclient.dto.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ObjectToListConverter {
    public List<Object> convert(Object source) {
        Map patientsPageMap = (Map) source;
        List<Object> contentObject = (List<Object>) patientsPageMap;
        return  contentObject;
    }
}