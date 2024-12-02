package by.lupach.patientaccountingsystemrestapiclient.converters;

import by.lupach.patientaccountingsystemrestapiclient.dto.PatientDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PatientByIdConverter implements Converter<String, PatientDTO> {
    @Override
    public PatientDTO convert(String source) {
        PatientDTO patient = new PatientDTO();
        patient.setId(Integer.parseInt(source));
        return patient;
    }
}
