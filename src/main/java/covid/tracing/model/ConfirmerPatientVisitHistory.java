package covid.tracing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmerPatientVisitHistory {

    private Integer confPatientId;

    private Integer confPatientVisitHistoryId;

    private String localId;

    private String province; // 지방

    private String visitType; // 방문한 곳의 타입

    private LocalDate firstDatetime;

    private LocalDate lastDatetime;

    private String streetNameAddr;

    private String streetNameAddrDesc;

    private Double latitude;

    private Double longitude;
}
