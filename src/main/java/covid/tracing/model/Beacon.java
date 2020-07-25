package covid.tracing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Beacon {

    private String uuid;
    private Integer major;
    private Integer minor;
    private Double latitude;
    private Double longitude;
    private String streetNameAddr;
    private String streetNameAddrDesc;
    private LocalDate installDatetime;
}
