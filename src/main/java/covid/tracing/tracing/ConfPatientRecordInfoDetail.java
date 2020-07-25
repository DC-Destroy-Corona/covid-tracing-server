package covid.tracing.tracing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfPatientRecordInfoDetail {

//    private ConfPatientDTO confPatient;
private Long confPatientId;

    private String gender;

    private String region;

    private LocalDate confDatetime;

    private ArrayList<ConfPatientMovingInfoDTO> movingInfoList;
}
