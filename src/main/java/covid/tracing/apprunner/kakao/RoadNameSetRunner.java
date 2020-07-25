package covid.tracing.apprunner.kakao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import covid.tracing.apprunner.csv.*;
import covid.tracing.apprunner.kakao.domain.Coord2AddressDTO;
import covid.tracing.apprunner.kakao.domain.Document;
import covid.tracing.mappers.ConfirmerPatientMapper;
import covid.tracing.model.ConfirmerPatient;
import covid.tracing.model.ConfirmerPatientVisitHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

@Component
public class RoadNameSetRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(new RoadNameSetRunner().getClass());

    @Autowired
    private ConfirmerPatientMapper confirmerPatientMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        logger.info("Hello RoadNameSet Runner");
//
//        ArrayList<ConfirmerPatientVisitHistory> confPatientVisitHistoryArrayList = confirmerPatientMapper.findAllConfPatientVisitHstWithoutRoadAddr();
//        logger.info(confPatientVisitHistoryArrayList.toString());
//
//        String apiKey = "";
//        String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2address.json";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "KakaoAK " + apiKey);
//        HttpEntity entity = new HttpEntity(headers);
//        RestTemplate restTemplate = new RestTemplate();
//
//        for (ConfirmerPatientVisitHistory confPatientVisitHistory: confPatientVisitHistoryArrayList) {
//            ResponseEntity<Coord2AddressDTO> Coord2Address = restTemplate.exchange(apiUrl+"?x="+confPatientVisitHistory.getLongitude()+"&y="+confPatientVisitHistory.getLatitude(), HttpMethod.GET, entity, Coord2AddressDTO.class);
//            logger.info(Coord2Address.getBody().getDocuments().toString());
//            if(Coord2Address.getBody().getDocuments().get(0).getRoad_address() != null) {
//                confPatientVisitHistory.setStreetNameAddr(Coord2Address.getBody().getDocuments().get(0).getRoad_address().getAddress_name());
//            } else {
//                confPatientVisitHistory.setStreetNameAddr(Coord2Address.getBody().getDocuments().get(0).getAddress().getAddress_name());
//            }
//            confPatientVisitHistory.setStreetNameAddrDesc(Coord2Address.getBody().getDocuments().get(0).getAddress().getAddress_name());
//            confirmerPatientMapper.updateRoadAddrOfConfPatientVisitHistory(
//                    confPatientVisitHistory.getConfPatientId(), confPatientVisitHistory.getConfPatientVisitHistoryId(),
//                    confPatientVisitHistory.getStreetNameAddr(), confPatientVisitHistory.getStreetNameAddrDesc());
//        }
    }
}