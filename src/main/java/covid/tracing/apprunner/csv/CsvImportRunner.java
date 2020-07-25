package covid.tracing.apprunner.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import covid.tracing.apprunner.csv.domain.Patient;
import covid.tracing.apprunner.csv.domain.PatientDTO;
import covid.tracing.apprunner.csv.domain.Route;
import covid.tracing.apprunner.csv.domain.RouteDTO;
import covid.tracing.apprunner.csv.mapper.PatientMapper;
import covid.tracing.apprunner.csv.mapper.RouteMapper;
import covid.tracing.mappers.ConfirmerPatientMapper;
import covid.tracing.model.ConfirmerPatient;
import covid.tracing.model.ConfirmerPatientVisitHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

@Component
public class CsvImportRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(new CsvImportRunner().getClass());

    @Autowired
    private ConfirmerPatientMapper confirmerPatientMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        logger.info("Hello Csv Runner");
//        File file = new File("./routes.csv");
//        if(!file.exists()) {
//            return;
//        }
//
//        CsvSchema schema = CsvSchema.builder()
//                .addColumn("confPatientId", CsvSchema.ColumnType.STRING)
//                .addColumn("localId", CsvSchema.ColumnType.STRING)
//                .addColumn("firstDatetime", CsvSchema.ColumnType.STRING)
//                .addColumn("lastDatetime", CsvSchema.ColumnType.STRING)
//                .addColumn("type", CsvSchema.ColumnType.STRING)
//                .addColumn("latitude", CsvSchema.ColumnType.STRING)
//                .addColumn("longitude", CsvSchema.ColumnType.STRING)
//                .addColumn("province", CsvSchema.ColumnType.STRING)
//                .build();
//
//        CsvMapper csvMapper = new CsvMapper();
//        MappingIterator mappingIterator = csvMapper.readerFor(RouteDTO.class).with(schema).readValues(file);
//
//        List<RouteDTO> routeResults = new ArrayList<RouteDTO>();
//        routeResults = mappingIterator.readAll();
//
//
//        Route route = null;
//        RouteDTO routeDTO = null;
//        RouteMapper routeMapper = new RouteMapper();
//
////        String apiKey = "";
////        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Authorization", "KakaoAK " + apiKey);
////        HttpEntity entity = new HttpEntity(headers);
////        RestTemplate restTemplate = new RestTemplate();
//
//        Map<Integer, List<Route>> mapConfPatientRoute = new HashMap<>();
//
//        // csv 파일로 부터 읽어들인 각 레코드를 하나씩 돌며 confPatientId를 기준으로 한 hashmap으로 변환하는 작업
//        for (int i = 0; i < routeResults.size(); i++) {
//            routeDTO = routeResults.get(i);
//            if(routeMapper.isValid(routeDTO)) {
//                //KakaoRoadAddrInfo fromKakao = restTemplate.exchange(apiUrl+"?query="+road, HttpMethod.GET, entity, Object.class);
//
//                route = routeMapper.convertToRoute(routeDTO);
//                route.setRoadAddr(null); // 카카오로 부터 데이터 받아와서 set 해줘야 함
//                if(mapConfPatientRoute.get(Integer.parseInt(routeDTO.getConfPatientId())) != null) {
//                    List<Route> routeList = mapConfPatientRoute.get(Integer.parseInt(routeDTO.getConfPatientId()));
//                    route.setRouteHistoryId(routeList.size() + 1);
//                    routeList.add(route);
//                } else {
//                    List<Route> routeList = new ArrayList<>();
//                    route.setRouteHistoryId(1);
//                    routeList.add(route);
//                    mapConfPatientRoute.put(Integer.parseInt(routeDTO.getConfPatientId()), routeList);
//                }
//            }
//        }
//
//        file = new File("./patients.csv");
//
//        schema = CsvSchema.builder()
//                .addColumn("confPatientId", CsvSchema.ColumnType.STRING)
//                .addColumn("gender", CsvSchema.ColumnType.STRING)
//                .addColumn("birth_year", CsvSchema.ColumnType.STRING)
//                .addColumn("province", CsvSchema.ColumnType.STRING)
//                .addColumn("confirmedDate", CsvSchema.ColumnType.STRING)
//                .build();
//        csvMapper = new CsvMapper();
//
//        mappingIterator = csvMapper.readerFor(PatientDTO.class).with(schema).readValues(file);
//        List<PatientDTO> patientResults = new ArrayList<PatientDTO>();
//        patientResults = mappingIterator.readAll();
//
//        PatientMapper patientMapper = new PatientMapper();
//        Map<Integer, Patient> mapPatient = new HashMap<>();
//
//        PatientDTO patientDTO = null;
//        Patient patient = null;
//        for(int i = 0; i < patientResults.size(); i++) {
//            patientDTO = patientResults.get(i);
//            if(patientMapper.isValid(patientDTO)) {
//                patient = patientMapper.convertToPatient(patientDTO);
//                logger.info(patient.getConfPatientId() + " ==> " + patient.toString());
//                mapPatient.put(patient.getConfPatientId(), patient);
//            }
//        }
//
//        logger.info(String.valueOf(mapConfPatientRoute.size()));
//
//        Map<Integer, List<Route>> sortedMapConfPatientRoute = sortMapByKey(mapConfPatientRoute);
//
//        ConfirmerPatient beforeConfirmerPatient = null;
//        ConfirmerPatient confirmerPatient = null;
//        LocalDate date = null;
//        String gender = null;
//        for (Map.Entry<Integer, List<Route>> entry : sortedMapConfPatientRoute.entrySet()) {
//            if(mapPatient.get(entry.getKey()) == null) {
//                date = beforeConfirmerPatient.getConfDatetime();
//                gender = "male";
//            } else {
//                date = mapPatient.get(entry.getKey()).getConfirmedDate();
//                gender = mapPatient.get(entry.getKey()).getGender();
//            }
//            confirmerPatient = ConfirmerPatient.builder()
//                    .confPatientId(entry.getKey())
//                    .region(entry.getValue().get(0).getProvince())
//                    .confDatetime(date)
//                    .gender(gender)
//                    .build();
//
//            // TODO) confirmerPatient 디비에 저장
//            beforeConfirmerPatient = confirmerPatient;
////            confirmerPatientMapper.insertConfPatient(confirmerPatient);
//            logger.info(entry.getKey() + " ==> " + entry.getValue().size());
//            logger.info(entry.getKey() + " ==> " + confirmerPatient.toString());
//            List<Route> tempRouteList = entry.getValue();
//            List<ConfirmerPatientVisitHistory> confirmerPatientVisitHistoryList = new ArrayList<>();
//            ConfirmerPatientVisitHistory confirmerPatientVisitHistory = null;
//            for (Route item : tempRouteList) {
//                logger.info(entry.getKey() + " (" + item.getRouteHistoryId() + ") ==> " + item.toString());
//                confirmerPatientVisitHistory = ConfirmerPatientVisitHistory.builder()
//                        .confPatientId(item.getConfPatientId())
//                        .confPatientVisitHistoryId(item.getRouteHistoryId())
//                        .localId(item.getLocalId())
//                        .province(item.getProvince())
//                        .visitType(item.getType())
//                        .firstDatetime(item.getFirstDatetime())
//                        .lastDatetime(item.getLastDatetime())
//                        .latitude(item.getLatitude())
//                        .longitude(item.getLongitude())
//                        .streetNameAddr(null)
//                        .streetNameAddrDesc(null)
//                        .build();
//                confirmerPatientVisitHistoryList.add(confirmerPatientVisitHistory);
//            }
//        }
    }


    public static LinkedHashMap<Integer, List<Route>> sortMapByKey(Map<Integer, List<Route>> map) {
        List<Map.Entry<Integer, List<Route>>> entries = new LinkedList<>(map.entrySet());
        Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));

        LinkedHashMap<Integer, List<Route>> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Route>> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
