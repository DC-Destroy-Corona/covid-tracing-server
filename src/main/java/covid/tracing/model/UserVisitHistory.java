package covid.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tika.sax.WriteOutContentHandler;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserVisitHistory {

    // TODO) 삭제해도 상관없을듯함
    //CNTCT_ST             boolean NOT NULL,

    private Long userId;
    private Integer userVisitHistoryId;
    private String beaconUuid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime firstDt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime lastDt;

    private long firstDatetime;
    private long lastDatetime;

    public UserVisitHistory() {}

    public void setFirstDt(LocalDateTime firstDt) {
        this.firstDt = firstDt;
        this.firstDatetime = Timestamp.valueOf(firstDt).getTime();
    }

    public void setLastDt(LocalDateTime lastDt) {
        this.lastDt = lastDt;
        this.lastDatetime = Timestamp.valueOf(lastDt).getTime();
    }
}
