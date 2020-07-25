package covid.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CntctInfo {

    /*
            CNTCT_INFO_SQ	int(11)	NO	PRI		auto_increment
            BEACON_UUID	char(36)	NO
            USER_SQ	int(11)	NO	MUL
            USER_VISIT_HST_ID	int(11)	NO
            CONF_PATIENT_SQ	int(11)	NO	MUL
            CONF_PATIENT_VISIT_HST_ID	int(11)	NO
            CNTCT_DT	date	NO
    */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime cntctDt;


    private Long cntctInfoId;

    private String beaconUUID;

    private Long userId;

    private Integer userVisitHstId;

    private Long confPatientId;

    private Integer confPatientVisitHstId;

    private long cntctDatetime;

    private Double latitude;

    private Double longitude;

    public CntctInfo() {
    }

    public void setCntctDt(LocalDateTime cntctDt) {
        this.cntctDt = cntctDt;
        this.cntctDatetime = Timestamp.valueOf(cntctDt).getTime();
    }

    public CntctInfo(Long confPatientId, Integer confPatientVisitHstId, UserVisitHistory userVisitHistory) {
        this.beaconUUID = userVisitHistory.getBeaconUuid();
        this.userId = userVisitHistory.getUserId();
        this.userVisitHstId = userVisitHistory.getUserVisitHistoryId();
        this.confPatientId = confPatientId;
        this.confPatientVisitHstId = confPatientVisitHstId;
        this.cntctDt = userVisitHistory.getFirstDt();
        this.cntctDatetime = Timestamp.valueOf(userVisitHistory.getFirstDt()).getTime();
    }
}
