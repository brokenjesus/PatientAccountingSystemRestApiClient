package by.lupach.patientaccountingsystemrestapiclient.converters;

import by.lupach.patientaccountingsystemrestapiclient.dto.WardDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WardByIdConverter implements Converter<String, WardDTO> {

    @Override
    public WardDTO convert(String source) {
        WardDTO patient = new WardDTO();
        patient.setId(Integer.parseInt(source));
        return patient;
    }
}
