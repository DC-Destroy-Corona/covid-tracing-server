package covid.tracing.mappers;

import covid.tracing.model.CntctInfo;
import covid.tracing.model.User;
import covid.tracing.model.UserVisitHistory;
import covid.tracing.model.customfind.CntctPatientMovingInfo;
import covid.tracing.model.customfind.CntctPatientInfo;
import covid.tracing.model.customfind.ContactorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface UserMapper {

//    void insertUser(UserDTO.SignUp signUp);
    void insertUser(User user);

    User findUserWithEmail(@Param("email") String email);

    User findUserWithId(Long id);

    ArrayList<CntctPatientMovingInfo> findAllCntctPatientMovingInfoById(@Param("cntctPatientId") Long id);

    ArrayList<CntctPatientInfo> findAllCntctPatient(
            @Param("region") String region, @Param("date") String date,
            @Param("offset") int offset, @Param("limit") int limit);

    int countCntctPatient(@Param("region") String region, @Param("date") String date);

    ArrayList<UserVisitHistory> findUserVisitHistoryListByBeaconUuId(@Param("uuid") String uuid);

    int countCntctPatientByConfPatientVisitHstId(
            @Param("confPatientId") Long confPatientId,
            @Param("confPatientVisitHstId") Integer confPatientVisitHstId);

    void insertContactInfoList(@Param("cnctInfoList") ArrayList<CntctInfo> cnctInfoList);

    ArrayList<CntctInfo> findAllCntctInfoByConfId(@Param("id") Long id);

    ArrayList<ContactorInfo> findAllCntctInfoByUserMovingInfo(
            @Param("userId") Long userId,
            @Param("userVisitHstId") Integer userVisitHstId);
}
