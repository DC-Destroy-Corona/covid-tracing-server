package covid.tracing.model.customfind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseMovingInfo {
    private String location;
    private Double latitude;
    private Double longitude;
}
