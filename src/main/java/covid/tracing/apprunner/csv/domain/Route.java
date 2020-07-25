package covid.tracing.apprunner.csv.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Route {

    private Integer confPatientId; // INTEGER

    private Integer routeHistoryId;

    private String localId;       // INTEGER

    private String province;

    private String type;

    private LocalDate firstDatetime; // LOCALDATETIME

    private LocalDate lastDatetime;  // LOCALDATETIME

    private String roadAddr;

    private String roadAddrDesc;

    private Double latitude;  // DOUBLE

    private Double longitude; // DOUBLE
}
