package covid.tracing.model.customfind;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CntctPatientInfo {

    private Integer cntctPatientId;  // 사용자 아이디 == userId

    private Integer confPatientId; // 접촉한 확진자 아이디

    private Integer visitPointNum; // 방문지점수

    private String gender;  // 성별

    // 아직 클라이언트 측으로 전달해줄지 확실하지 않은 데이터지만 일단은 조회 목록 포함
    private String email;
    private String birthdate;
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;
}
