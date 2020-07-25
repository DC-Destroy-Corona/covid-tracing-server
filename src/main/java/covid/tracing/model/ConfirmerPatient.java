package covid.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import covid.tracing.tracing.ConfPatientDTO;
import covid.tracing.tracing.ConfPatientRecordInfoDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ConfirmerPatient {

    private Long confPatientId; // pk integer auto_increment

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate confDt; // datetime

    private long confDatetime;

    private String region; // varchar(8)

    private String gender; // string (MAN"남자", WOMAN"여자")

    public ConfirmerPatient() {
    }

    public void setConfDt(LocalDate confDt) {
        this.confDt = confDt;
        this.confDatetime = Timestamp.valueOf(confDt.atStartOfDay()).getTime();
    }

    public static ConfirmerPatient create(ConfPatientRecordInfoDetail confPatientRecordInfoDetail) {
        return ConfirmerPatient.builder()
                .confPatientId(confPatientRecordInfoDetail.getConfPatientId())
                .gender(confPatientRecordInfoDetail.getGender())
                .confDt(confPatientRecordInfoDetail.getConfDatetime())
                .confDatetime(Timestamp.valueOf(confPatientRecordInfoDetail.getConfDatetime().atStartOfDay()).getTime())
                .region(confPatientRecordInfoDetail.getRegion())
                .build();
    }
}
