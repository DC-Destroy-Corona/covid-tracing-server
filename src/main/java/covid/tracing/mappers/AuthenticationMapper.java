package covid.tracing.mappers;

import covid.tracing.common.datatype.Role;
import covid.tracing.model.Authentication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthenticationMapper {

    // user
    void insertUserAuthentication(
            @Param("authKey") String authKey,
            @Param("email") String email,
            @Param("role") Role role);

    Authentication findUserAuthentication(
            @Param("authKey") String authKey,
            @Param("email") String email,
            @Param("role") Role role);

    Authentication findUserAuthentication(
            @Param("email") String email,
            @Param("role") Role role);


    void deleteUserAuthentication(
            @Param("email") String email,
            @Param("role") Role user);

    void updateUserAuthentication(
            @Param("authKey") String authKey,
            @Param("email") String email,
            @Param("role") Role user);


    //epid
}
