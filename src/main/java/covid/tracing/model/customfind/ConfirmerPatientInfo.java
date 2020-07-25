package covid.tracing.model.customfind;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ConfirmerPatientInfo {

    // ref) https://araikuma.tistory.com/476

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate confDt;

    private Integer confPatientId; // pk integer auto_increment

    private long confDatetime; // datetime

    private String region; // varchar(8)

    private String gender; // string (MAN"남자", WOMAN"여자")

    private Integer visitPointNum; // 방문 지점 수

    private Integer contactorNum; // 접촉자 수

    public ConfirmerPatientInfo() {
    }

    public void setConfDt(LocalDate confDt) {
        this.confDatetime = Timestamp.valueOf(confDt.atStartOfDay()).getTime();
    }
}
