package covid.tracing.rabbitmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beacon {
    private String uuid;
    private String major;
    private String minor;
    private String head;
    private String tail;
}
