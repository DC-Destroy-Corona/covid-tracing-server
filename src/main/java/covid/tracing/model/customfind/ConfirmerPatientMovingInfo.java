package covid.tracing.model.customfind;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConfirmerPatientMovingInfo extends BaseMovingInfo {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate firstDt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate lastDt;

    private long firstDatetime;
    private long lastDatetime;
    private Integer personNum;//cntctPatientNum;
    private Integer confPatientVisitHstId;

    private String location;
    private Double latitude;
    private Double longitude;
    private ArrayList<ContactorInfo> contactorInfo;
    private String province;
    private String type;


    public ConfirmerPatientMovingInfo() {
    }

    public void setFirstDt(LocalDate startDt) {
        this.firstDatetime = Timestamp.valueOf(startDt.atStartOfDay()).getTime();
    }

    public void setLastDt(LocalDate lastDt) {
        this.lastDatetime = Timestamp.valueOf(lastDt.atStartOfDay()).getTime();
    }

    //    public ConfirmerPatientMovingInfo(String location, Double latitude, Double longitude, LocalDateTime datetime, Integer cntctPatientNum, Integer confPatientVisitHstId) {
//        super(location, latitude, longitude);
//        this.datetime = datetime;
//        this.cntctPatientNum = cntctPatientNum;
//        this.confPatientVisitHstId = confPatientVisitHstId;
//    }
}
