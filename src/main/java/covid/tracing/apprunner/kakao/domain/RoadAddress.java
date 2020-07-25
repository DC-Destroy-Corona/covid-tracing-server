package covid.tracing.apprunner.kakao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadAddress {
    private String address_name;
    private String region_1depth_name;
    private String region_2depth_name;
    private String region_3depth_name;
    private String road_name;
    private String underground_yn;
    private String main_building_no;
    private String sub_building_no;
    private String building_name;
    private String zone_no;
}
