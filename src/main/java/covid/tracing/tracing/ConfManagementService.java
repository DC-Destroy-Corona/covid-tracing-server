package covid.tracing.tracing;

import covid.tracing.common.constants.Regions;
import covid.tracing.mappers.ConfirmerPatientMapper;
import covid.tracing.model.ConfirmerPatient;
import covid.tracing.model.customfind.ConfirmerPatientMovingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfManagementService {

    private ConfirmerPatientMapper confirmerPatientMapper;

    @Autowired
    public ConfManagementService(ConfirmerPatientMapper confirmerPatientMapper) {
        this.confirmerPatientMapper = confirmerPatientMapper;
    }

    public void setAllConfPatient(Integer confPageIndex, Map<String, Object> response) {

        Pagination pagination = new Pagination(confPageIndex, 11);
        int totalConfPatient = confirmerPatientMapper.countConfPatient(Regions.KOREA, "0000-00-00");
        pagination.setTotalCnt(totalConfPatient);

        ArrayList<ConfirmerPatient> confPatientList = confirmerPatientMapper.findAllConfPatient(pagination.getOffset(), pagination.getLimit());

        response.put("currentConfPageIndex", confPageIndex);
        response.put("totalConfPageIndex", pagination.calAndGetTotalPageIndex());
        response.put("totalConfPatient", pagination.getTotalCnt());
        response.put("confPatientList", confPatientList);
    }

    public ConfirmerPatient recordNewConfPatient(ConfPatientRecordInfoDetail confPatientRecordInfoDetail) {
        ConfirmerPatient newConfPatient = ConfirmerPatient.create(confPatientRecordInfoDetail);
        confirmerPatientMapper.deleteConfPatientById(confPatientRecordInfoDetail.getConfPatientId());
        confirmerPatientMapper.insertConfPatient(newConfPatient);
        return newConfPatient;
    }

    public Object getAllMovingInfoListByConfId(Long confId) {

        HashMap<String, Object> response = new HashMap<>();

        ArrayList<ConfirmerPatientMovingInfo> confirmerPatientMovingInfoList = confirmerPatientMapper.findAllConfPatientMovingInfoByConfId(confId);

        response.put("confPatientId", confId);
        response.put("confPatientMovingInfo", confirmerPatientMovingInfoList);

        return response;
    }
}
