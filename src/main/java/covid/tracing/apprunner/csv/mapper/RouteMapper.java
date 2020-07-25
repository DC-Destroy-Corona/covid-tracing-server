package covid.tracing.apprunner.csv.mapper;

import covid.tracing.apprunner.csv.domain.Route;
import covid.tracing.apprunner.csv.domain.RouteDTO;

import java.time.LocalDate;

public class RouteMapper {

//    private static final Logger logger = LoggerFactory.getLogger(new CsvRunner().getClass());

    public boolean isValid(RouteDTO routeDTO) {
        if(routeDTO.getConfPatientId().length() == 0) {
            return false;
        }
        if(routeDTO.getFirstDatetime().length() > 9 ||
                routeDTO.getLastDatetime().length() > 9 ||
                routeDTO.getLastDatetime().length() == 0) {
            return false;
        }
        if(routeDTO.getLatitude().length() == 0
                || routeDTO.getLongitude().length() == 0) {
            return false;
        }
        return true;
    }

    public Route convertToRoute(RouteDTO routeDTO) {
        Route route = Route.builder()
                .confPatientId(Integer.parseInt(routeDTO.getConfPatientId()))
                .localId(routeDTO.getLocalId())
                .firstDatetime(this.toLocalDate(routeDTO.getFirstDatetime())) //
                .lastDatetime(this.toLocalDate(routeDTO.getLastDatetime())) //
                .type(routeDTO.getType())
                .latitude(Double.parseDouble(routeDTO.getLatitude()))
                .longitude(Double.parseDouble(routeDTO.getLongitude()))
                .province(routeDTO.getProvince())
                .roadAddr(null) //
                .build();

        return route;
    }

    private LocalDate toLocalDate(String date) {
        String[] dateArr = date.split("/");
        return LocalDate.of(2020, Integer.valueOf(dateArr[0]), Integer.valueOf(dateArr[1]));
    }
}
