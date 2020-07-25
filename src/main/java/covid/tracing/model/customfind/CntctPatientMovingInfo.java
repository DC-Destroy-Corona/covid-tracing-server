package covid.tracing.model.customfind;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data // Mybatis 가 기본 생성자를 호출하게 하지 않기 위해서 @NoArgsConstructor를 제거
public class CntctPatientMovingInfo extends BaseMovingInfo {

    // ref) https://araikuma.tistory.com/476

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime firstDt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime lastDt;

    private long firstDatetime;
    private long lastDatetime;
//    private Integer confPatientId; // 이거를 빼고

    private Integer personNum;//confPatientNum; // 이거를 추가 : 해당 지점에서 접촉한 확진자의 수
    private Integer userVisitHstId;
    private ArrayList<ContactorInfo> contactorInfo;


    public CntctPatientMovingInfo() {
    }

    public void setFirstDt(LocalDateTime firstDt) {
        this.firstDatetime = Timestamp.valueOf(firstDt).getTime();
    }

    public void setLastDt(LocalDateTime lastDt) {
        this.lastDatetime = Timestamp.valueOf(lastDt).getTime();
    }
}
