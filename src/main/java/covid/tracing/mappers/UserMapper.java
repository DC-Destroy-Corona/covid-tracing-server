package covid.tracing.mappers;

import covid.tracing.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

//    void insertUser(UserDTO.SignUp signUp);
    void insertUser(User user);

    User findUserWithEmail(@Param("email") String email);

    User findUserWithId(Long id);
}
