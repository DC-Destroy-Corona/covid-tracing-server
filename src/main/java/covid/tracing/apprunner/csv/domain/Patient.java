package covid.tracing.apprunner.csv.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    private Integer confPatientId;
    private String gender;
    private LocalDate confirmedDate;
}
