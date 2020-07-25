package covid.tracing.tracing;

import covid.tracing.common.datatype.DatatypeService;
import covid.tracing.common.datatype.MutableInt;
import covid.tracing.common.utils.StreamUtil;
import covid.tracing.mappers.ConfirmerPatientMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.customfind.ConfirmerPatientInfo;
import covid.tracing.model.customfind.CntctPatientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CovidInfoFilterService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConfirmerPatientMapper confirmerPatientMapper;
    private UserMapper userMapper;
    private DatatypeService datatypeService;

    @Autowired
    public CovidInfoFilterService(ConfirmerPatientMapper confirmerPatientMapper, UserMapper userMapper,
                                  DatatypeService datatypeService) {
        this.confirmerPatientMapper = confirmerPatientMapper;
        this.userMapper = userMapper;
        this.datatypeService = datatypeService;
    }

    // 메인 페이지에 처음 진입 시 보여줌
    public Object findAndFilter(String region, String date, Integer currentConfPageIndex, Integer currentCntctPageIndex) {
        logger.info("process find and filter covid info { region: " + region + " date: " + date + " currentConfPageIndex: " + currentConfPageIndex + " currentCntctPageIndex: " + currentCntctPageIndex);

        Map<String, Object> info = new HashMap<String, Object>();

        info.put("selectRegion", region); // TODO) 선택 지역
        info.put("date", date); // TODO) 선택 날짜

        // 확진자 처리
        Pagination confPagination = new Pagination(currentConfPageIndex);

        int totalConfPatient = confirmerPatientMapper.countConfPatient(region, date);
        confPagination.setTotalCnt(totalConfPatient);
        logger.info("execute query method) confirmerPatientMapper.findAllConfPatient()...");
        ArrayList<ConfirmerPatientInfo> confPatientList = confirmerPatientMapper.findAllConfPatientInfo(region, date, confPagination.getOffset(), confPagination.getLimit());

        info.put("currentConfPageIndex", currentConfPageIndex); // TODO) 현재 확진자 페이지 인덱스
        info.put("totalConfPageIndex", confPagination.calAndGetTotalPageIndex()); // TODO) 확진자 리스트 페이지 개수
        info.put("totalConfPatient", confPagination.getTotalCnt()); // TODO) 확진자 수
        info.put("confPatientList", confPatientList); // TODO) 확진자 리스트


        // 접촉자 처리
        Pagination cntctPagination = new Pagination(currentCntctPageIndex);

//        // 중복 제거된 총 접촉자 수, ref(컬렉션 중복 제거) : https://stackoverflow.com/questions/23699371/java-8-distinct-by-property
//        ArrayList<ContactInfo> contactInfoListSet = contactInfoList.stream().filter(
//                StreamUtil.distinctByKey(ContactInfo::getUserId)).
//                collect(Collectors.toCollection(ArrayList::new));
//        info.put("totalCntctPatient", contactInfoListSet.size()); // 오늘 총 접촉자 수 (중복 제거 O)

        int cntctPatientCnt = userMapper.countCntctPatient(region, date);
        cntctPagination.setTotalCnt(cntctPatientCnt);
        logger.info("execute query method) userMapper.findAllContactorInfo()...");
        ArrayList<CntctPatientInfo> contactorList = userMapper.findAllCntctPatient(region, date, cntctPagination.getOffset(), cntctPagination.getLimit());
        // 접촉자 중복 제거 cntctPatientId 를 기준,

        info.put("currentCntctPageIndex", currentCntctPageIndex); // TODO) 현재 접촉자 페이지 인덱스
        info.put("totalCntctPageIndex", cntctPagination.calAndGetTotalPageIndex()); // TODO) 접촉자 리스트 페이지 개수
        info.put("totalCntctPatient", cntctPagination.getTotalCnt()); // TODO) 접촉자 수
        info.put("cntctPatientList", contactorList); // TODO) 접촉자 리스트, 방문자 중복을 걸러내는 건 아직 못함


        // 지역별 확진자
        // - hashmap 테이블을 만든다 map = {"서울": 0, "대구": 0, "부산": 0,.....}
        HashMap<String, MutableInt> confPatientStatsByRegion = new HashMap<String, MutableInt>();
        confPatientList.stream().forEach(confPatient ->
                datatypeService.countMutableIntMap(confPatientStatsByRegion, confPatient.getRegion()));
        info.put("regions", datatypeService.convertToHashMap(confPatientStatsByRegion)); // TODO) 지역별 확진자 현황

        return info;
    }
}
