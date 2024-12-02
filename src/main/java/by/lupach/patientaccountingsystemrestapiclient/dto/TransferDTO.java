package by.lupach.patientaccountingsystemrestapiclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.print.DocFlavor;
import java.sql.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private Integer id;
    private String date;
    private WardDTO ward;
    private PatientDTO patient;
}
