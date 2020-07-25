package covid.tracing.apprunner.csv.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private String confPatientId;
    private String gender;
    private String birth_year;
    private String province;
    private String confirmedDate;
}
