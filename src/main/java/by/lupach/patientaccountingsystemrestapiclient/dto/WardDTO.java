package by.lupach.patientaccountingsystemrestapiclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WardDTO {
    private Integer id;
    private String phone;
    private String number;
    private Integer bedPlaceCount;
    private int occupiedBeds;
}
