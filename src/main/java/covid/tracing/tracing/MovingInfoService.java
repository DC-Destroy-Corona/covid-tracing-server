package covid.tracing.tracing;

import covid.tracing.mappers.ConfirmerPatientMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.CntctInfo;
import covid.tracing.model.customfind.CntctPatientMovingInfo;
import covid.tracing.model.customfind.ConfirmerPatientMovingInfo;
import covid.tracing.model.customfind.ContactorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MovingInfoService {

    private ConfirmerPatientMapper confirmerPatientMapper;
    private UserMapper userMapper;

    @Autowired
    public MovingInfoService(ConfirmerPatientMapper confirmerPatientMapper, UserMapper userMapper) {
        this.confirmerPatientMapper = confirmerPatientMapper;
        this.userMapper = userMapper;
    }

    public HashMap<String, Object> getConfPatientMovingInfoList(Long confPatientId) {

        HashMap<String, Object> response = new HashMap<>();

        ArrayList<ConfirmerPatientMovingInfo> movingInfoList = confirmerPatientMapper.findAllConfPatientMovingInfoByConfId(confPatientId);
        int count = 0;
        for (ConfirmerPatientMovingInfo itemMovingInfo : movingInfoList) {
            //set cntctPatientNum
            count = userMapper.countCntctPatientByConfPatientVisitHstId(confPatientId, itemMovingInfo.getConfPatientVisitHstId());
            itemMovingInfo.setPersonNum(count);
            count = 0;

            // set contactorInfoList
            ArrayList<ContactorInfo> contactorInfoList = confirmerPatientMapper.findAllCntctInfoByConfMovingInfo(confPatientId, itemMovingInfo.getConfPatientVisitHstId());
            itemMovingInfo.setContactorInfo(contactorInfoList);
        }

//        ArrayList<CntctInfo> cntctInfoListByConfId = userMapper.findAllCntctInfoByConfId(confPatientId);

        response.put("type", 1);
        response.put("id", confPatientId);
        response.put("movingInfo", movingInfoList);
//        response.put("cntctPatientInfo", cntctInfoListByConfId);

        return response;
    }

    public HashMap<String, Object> getCntctPatientMovingInfoList(Long cntctPatientId) {

//        public List<CntctPatientMovingInfo> movingInfo = movingInfoList;
        HashMap<String, Object> response = new HashMap<>();

        ArrayList<CntctPatientMovingInfo> movingInfoList = userMapper.findAllCntctPatientMovingInfoById(cntctPatientId);

        for (CntctPatientMovingInfo itemMovingInfo : movingInfoList) {
            // set contactorInfoList
            ArrayList<ContactorInfo> contactorInfoList = userMapper.findAllCntctInfoByUserMovingInfo(cntctPatientId, itemMovingInfo.getUserVisitHstId());
            itemMovingInfo.setContactorInfo(contactorInfoList);
        }

        response.put("type", 2);
        response.put("id", cntctPatientId);
        response.put("movingInfo", movingInfoList);

        return response;
    }

    public void recordConfPatientMovingInfoList(Long confPatientId, ArrayList<ConfPatientMovingInfoDTO> confPatientMovingInfoList) {
        confirmerPatientMapper.deleteAllConfPatientMovingInfoByConfPatientId(confPatientId);
        confirmerPatientMapper.insertConfPatientMovingInfoList(confPatientId, confPatientMovingInfoList);
    }
}
