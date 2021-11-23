package platform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.model.Code;
import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends CrudRepository<Code, Long> {

    int limit = 10;
    String query = "SELECT * FROM code WHERE time <= 0 AND views <= 0 ORDER BY id DESC limit " + limit;
    String countQuery = "SELECT * FROM code";

    @Query(value = query, countQuery = countQuery, nativeQuery = true)
    List<Code> findLatest();

    Optional<Code> findCodeByUuid(String uuid);
}
