package covid.tracing.tracing;

import covid.tracing.mappers.BeaconMapper;
import covid.tracing.model.Beacon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class BeaconManagementService {

    private BeaconMapper beaconMapper;

    @Autowired
    public BeaconManagementService(BeaconMapper beaconMapper) {
        this.beaconMapper = beaconMapper;
    }

    public void setAllBeaconList(Integer beaconPageIndex, Map<String, Object> response) {

        Pagination pagination = new Pagination(beaconPageIndex, 10);
        int totalBeacon = beaconMapper.countBeacon();
        pagination.setTotalCnt(totalBeacon);

        ArrayList<Beacon> beaconList = beaconMapper.findAllBeacon(pagination.getOffset(), pagination.getLimit());

        response.put("currentBeaconPageIndex", beaconPageIndex);
        response.put("totalBeaconPageIndex", pagination.calAndGetTotalPageIndex());
        response.put("totalBeacon", pagination.getTotalCnt());
        response.put("beaconList", beaconList);
    }

    public void recordNewBeacon(Beacon beacon) {
        beaconMapper.insertBeacon(beacon);
    }
}
