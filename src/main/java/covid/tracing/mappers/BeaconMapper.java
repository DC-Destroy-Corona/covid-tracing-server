package covid.tracing.mappers;

import covid.tracing.model.Beacon;
import covid.tracing.rabbitmq.domain.UserLocationMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Mapper
@Repository
public interface BeaconMapper {

    ArrayList<covid.tracing.model.Beacon> findAdjBeaconListByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude);

    void insertUserLocationMessageByBeacon(
            @Param("userId") Long userId, @Param("uuid") String uuid,
            @Param("head") LocalDateTime head, @Param("tail") LocalDateTime tail);

    ArrayList<Beacon> findAllBeacon(@Param("offset") int offset, @Param("limit") int limit);

    int countBeacon();

    void insertBeacon(@Param("beacon") Beacon beacon);
}
