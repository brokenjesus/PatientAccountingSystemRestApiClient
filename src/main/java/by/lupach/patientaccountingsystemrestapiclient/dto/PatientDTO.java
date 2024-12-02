package by.lupach.patientaccountingsystemrestapiclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Integer id;
    public Integer height;
    private String name;
    private Gender gender;
    private Integer age;
    private String hairColor;
    private String distinctiveCharacteristics;
}

