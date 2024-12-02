package by.lupach.patientaccountingsystemrestapiclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionDischargeHistoryDTO {
    private Integer id;
    private PatientDTO patient;
    private Reason reason;
    private Date date;
    private String diagnosis;
    private AdmissionMethod admissionMethod = null;
}
enum AdmissionMethod {
    AMBULANCE, REFERRAL
}





