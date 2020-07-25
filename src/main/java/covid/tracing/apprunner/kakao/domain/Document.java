package covid.tracing.apprunner.kakao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    RoadAddress road_address;
    Address address;
}
