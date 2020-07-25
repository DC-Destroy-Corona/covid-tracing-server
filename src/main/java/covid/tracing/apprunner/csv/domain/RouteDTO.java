package covid.tracing.apprunner.csv.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class RouteDTO {

    private String confPatientId; // INTEGER

    private String localId;       // INTEGER

    private String firstDatetime; // LOCALDATETIME

    private String lastDatetime;  // LOCALDATETIME

    private String type;

    private String latitude;  // DOUBLE

    private String longitude; // DOUBLE

    private String province;

    public RouteDTO() {
    }

    public RouteDTO(String confPatientId, String localId, String firstDatetime, String lastDatetime, String type, String latitude, String longitude, String province) {
        this.confPatientId = confPatientId;
        this.localId = localId;
        this.firstDatetime = firstDatetime;
        this.lastDatetime = lastDatetime;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province = province;
    }


    public String getConfPatientId() {
        return confPatientId;
    }

    public void setConfPatientId(String confPatientId) {
        this.confPatientId = confPatientId;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getFirstDatetime() {
        return firstDatetime;
    }

    public void setFirstDatetime(String firstDatetime) {
        this.firstDatetime = firstDatetime;
    }

    public String getLastDatetime() {
        return lastDatetime;
    }

    public void setLastDatetime(String lastDatetime) {
        this.lastDatetime = lastDatetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
