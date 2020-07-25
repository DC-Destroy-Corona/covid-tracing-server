package covid.tracing.tracing;

import covid.tracing.mappers.BeaconMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.Beacon;
import covid.tracing.model.CntctInfo;
import covid.tracing.model.UserVisitHistory;
import covid.tracing.model.customfind.ConfirmerPatientMovingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClassificationService {

    private BeaconMapper beaconMapper;
    private UserMapper userMapper;

    @Autowired
    public ClassificationService(BeaconMapper beaconMapper, UserMapper userMapper) {
        this.beaconMapper = beaconMapper;
        this.userMapper = userMapper;
    }

    public void classifyCnctPatient(Long confPatientId, ArrayList<ConfPatientMovingInfoDTO> confPatientMovingInfoList) {

        ArrayList<CntctInfo> cnctInfoList = new ArrayList<>();
        CntctInfo cnctInfo = null;

        for(ConfPatientMovingInfoDTO itemConfPatientMovingInfo : confPatientMovingInfoList) {

            // 해당 위치와 10m 원경에 있는 인접한 비콘 리스트 조회
            // TODO) 쿼리 구현
            ArrayList<Beacon> adjBeaconList =
                    beaconMapper.findAdjBeaconListByLocation(itemConfPatientMovingInfo.getLatitude(), itemConfPatientMovingInfo.getLongitude());

            for(Beacon itemBeacon : adjBeaconList) {
                // TODO) 쿼리 구현
                ArrayList<UserVisitHistory> userVisitHistoryList = userMapper.findUserVisitHistoryListByBeaconUuId(itemBeacon.getUuid());

                for (UserVisitHistory itemUserVisitHistory : userVisitHistoryList) {
                    cnctInfo = new CntctInfo(confPatientId, itemConfPatientMovingInfo.getConfPatientMovingInfoId(), itemUserVisitHistory);
                    cnctInfoList.add(cnctInfo);
                }
            }
        }
        // TODO) 쿼리 구현
        if(cnctInfoList.size() != 0) {
            userMapper.insertContactInfoList(cnctInfoList);
        }
    }
}
