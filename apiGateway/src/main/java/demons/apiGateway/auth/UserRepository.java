package demons.apiGateway.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by demons on 2017/6/27.
 */
@Repository
public interface UserRepository extends JpaRepository<KeyUserInfo, Long> {

    List<KeyUserInfo> findByEmail(String email);
}
