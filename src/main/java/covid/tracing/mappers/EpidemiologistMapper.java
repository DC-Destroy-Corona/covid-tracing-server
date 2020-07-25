package covid.tracing.mappers;

import covid.tracing.model.Epidemiologist;
import covid.tracing.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EpidemiologistMapper {

    void insertEpidemiologist(Epidemiologist epidemiologist);

    Epidemiologist findEpidemiologistWithEmail(@Param("email") String email);

    Epidemiologist findEpidemiologistWithId(@Param("id") Long id);
}
