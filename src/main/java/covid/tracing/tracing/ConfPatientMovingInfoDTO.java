package covid.tracing.tracing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfPatientMovingInfoDTO {

    private Integer confPatientMovingInfoId;
    private String localId;
    private String streetNameAddr;
    private String streetNameAddrDesc;
    private LocalDate firstDatetime;
    private LocalDate lastDatetime;
    private Double latitude;
    private Double longitude;
    private String type;
    private String province;

    /*
    			"confPatientMovingInfoId": 3,
				"roadNameAddr": "",
					"firstDate": "",
					"lastDate": "",
					"latitude":	123.1312312321, // 위도
					"longitude": 123.1312312321, // 경도
					"type": "",
					"province": ""
    */
}
