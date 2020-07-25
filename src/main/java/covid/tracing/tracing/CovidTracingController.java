package covid.tracing.tracing;

import covid.tracing.model.Beacon;
import covid.tracing.model.ConfirmerPatient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/epid")
public class CovidTracingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CovidInfoFilterService covidInfoFilterService;
    private MovingInfoService movingInfoService;
    private ClassificationService classificationService;
    private ConfManagementService confManagementService;
    private BeaconManagementService beaconManagementService;

    @Autowired
    public CovidTracingController(CovidInfoFilterService covidInfoFilterService, MovingInfoService movingInfoService, ClassificationService classificationService,
                                  ConfManagementService confManagementService, BeaconManagementService beaconManagementService) {
        this.covidInfoFilterService = covidInfoFilterService;
        this.movingInfoService = movingInfoService;
        this.classificationService = classificationService;
        this.confManagementService = confManagementService;
        this.beaconManagementService = beaconManagementService;
    }

    @GetMapping("/login-success/covid-info/edit/conf/{confPageIndex}/beacon/{beaconPageIndex}")
    public ResponseEntity getCovidInfoEdit(@PathVariable("confPageIndex") Integer confPageIndex,
                                           @PathVariable("beaconPageIndex") Integer beaconPageIndex) {

        Map<String, Object> response = new HashMap<>();

        beaconManagementService.setAllBeaconList(beaconPageIndex, response);
        confManagementService.setAllConfPatient(confPageIndex, response);

        return ResponseEntity.ok().body(new Object(){
            public HttpStatus status = HttpStatus.OK;
            public String msg = "...";
            public Map<String, Object> data = response;
        });
    }

    @GetMapping("/login-success/covid-info/{region}/{date}/cntct/{cntctPageIndex}/conf/{confPageIndex}")
    public ResponseEntity getCovidInfoStatus(@PathVariable("region") String region,
                                             @PathVariable("date") String date,
                                             @PathVariable("cntctPageIndex") Integer currentCntctPageIndex,
                                             @PathVariable("confPageIndex") Integer currentConfPageIndex) {
        logger.info("\naccept request about /epid/login-success/covid-info/...");

        Object info = covidInfoFilterService.findAndFilter(region, date, currentConfPageIndex, currentCntctPageIndex);

        return ResponseEntity.ok().body(new Object(){
            public HttpStatus status = HttpStatus.OK;
            public String msg = "...";
            public Object data = info;
        });
    }


    @GetMapping("/confPatient/{confPatientId}/movingInfo")
    public ResponseEntity getConfPatientMovingInfo(@PathVariable("confPatientId") Long id) {

        Object responseData = movingInfoService.getConfPatientMovingInfoList(id);

        return ResponseEntity.ok().body(new Object(){
            public HttpStatus status = HttpStatus.OK;
            public String msg = "...";
            public Object data = responseData;
        });
    }


    @GetMapping("/cntctPatient/{cntctPatientId}/movingInfo")
    public ResponseEntity getCntctPatientMovingInfo(@PathVariable("cntctPatientId") Long id) {

        Object responseData = movingInfoService.getCntctPatientMovingInfoList(id);

        return ResponseEntity.ok().body(new Object(){
            public HttpStatus status = HttpStatus.OK;
            public String msg = "...";
            public Object data = responseData;
        });
    }


    @PostMapping("/confPatient")
    public ResponseEntity recordConfPatient(@RequestBody ConfPatientDTO confPatient) throws URISyntaxException {
        logger.info(confPatient.toString());
//        ConfirmerPatient confirmerPatient = confManagementService.recordNewConfPatient(confPatient);
        return ResponseEntity.created(new URI("/")).body(new Object(){
            public HttpStatus status = HttpStatus.CREATED;
            public String message = "...";
            public Object data = null;
//            public Object data = new Object(){
//                public ConfirmerPatient confPatient = confirmerPatient;
//            };
        });
    }


    @PostMapping("/record/confPatient/{confPatientId}/movingInfoList")
    public ResponseEntity recordConfPatientMovingInfoList(//@RequestBody ArrayList<ConfPatientMovingInfoDTO> confPatientMovingInfoList,
                                                          @RequestBody ConfPatientRecordDTO confPatient,
                                                          @PathVariable("confPatientId") Long confPatientId) throws URISyntaxException {

        // 확진자 저장
        ConfirmerPatient confirmerPatient = confManagementService.recordNewConfPatient(confPatient.getConfPatient());

        // DB에 동선 저장
        movingInfoService.recordConfPatientMovingInfoList(confPatientId, confPatient.getConfPatient().getMovingInfoList());

        // Classification 수행
        classificationService.classifyCnctPatient(confPatientId, confPatient.getConfPatient().getMovingInfoList());

        return ResponseEntity.created(new URI("/")).body(new Object(){
            public HttpStatus status = HttpStatus.CREATED;
            public String message = "...";
            public Object data = null;
        });
    }


    @PostMapping("/beacon")
    public ResponseEntity recordBeaocn(@RequestBody Beacon beaconDTO) throws URISyntaxException {

        beaconManagementService.recordNewBeacon(beaconDTO);

        return ResponseEntity.created(new URI("/")).body(new Object(){
            public HttpStatus status = HttpStatus.CREATED;
            public String message = "...";
            public Object data = new Object(){
                public Beacon beacon = beaconDTO;
            };
        });
    }


    // edit page에서 호출하는 API
    @PostMapping("/edit/confPatient/{confPatientId}/movingInfoList")
    public ResponseEntity getConfPatientMovingInfoList(@PathVariable("confPatientId") Long id) throws URISyntaxException {

        Object responseData = confManagementService.getAllMovingInfoListByConfId(id);

        return ResponseEntity.created(new URI("/epid/edit/confPatient/confPatientId/movingInfoList")).body(new Object(){
            public HttpStatus status = HttpStatus.CREATED;
            public String msg = "...";
            public Object data = responseData;
        });
    }
}
