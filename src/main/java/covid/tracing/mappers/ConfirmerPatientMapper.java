package covid.tracing.mappers;

import covid.tracing.model.ConfirmerPatient;
import covid.tracing.model.ConfirmerPatientVisitHistory;
import covid.tracing.model.customfind.ConfirmerPatientInfo;
import covid.tracing.model.customfind.ConfirmerPatientMovingInfo;
import covid.tracing.model.customfind.ContactorInfo;
import covid.tracing.tracing.ConfPatientMovingInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface ConfirmerPatientMapper {

    ArrayList<ConfirmerPatient> findAllTodayConfPatient();

    void insertConfPatient(ConfirmerPatient confirmerPatient);

    void insertConfPatientMovingInfoList(
            @Param("confPatientId") Long confPatientId,
            @Param("confPatientMovingInfoList") ArrayList<ConfPatientMovingInfoDTO> confPatientMovingInfoList);

    ArrayList<ConfirmerPatientInfo> findAllConfPatientInfo(
            @Param("region") String region, @Param("date") String date,
            @Param("offset") int offset, @Param("limit") int limit);

    ArrayList<ConfirmerPatientMovingInfo> findAllConfPatientMovingInfoByConfId(@Param("id") Long id);

    ArrayList<ContactorInfo> findAllCntctInfoByConfMovingInfo(
            @Param("confPatientId") Long confPatientId,
            @Param("confPatientVisitHstId") Integer confPatientVisitHstId);

    int countConfPatient(@Param("region") String region, @Param("date") String date);


    ArrayList<ConfirmerPatientVisitHistory> findAllConfPatientVisitHstWithoutRoadAddr();
    void updateRoadAddrOfConfPatientVisitHistory(
            @Param("confPatientId") Integer confPatientId, @Param("confPatientVisitHistoryId") Integer confPatientVisitHistoryId,
            @Param("streetNameAddr") String streetNameAddr, @Param("streetNameAddrDesc") String streetNameAddrDesc);

    void deleteAllConfPatientMovingInfoByConfPatientId(@Param("confPatientId") Long confPatientId);

    ArrayList<ConfirmerPatient> findAllConfPatient(@Param("offset") int offset, @Param("limit") int limit);

    void deleteConfPatientById(@Param("confPatientId") Long confPatientId);
}