package demons.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by demons on 2017/6/27.
 */
public interface UserRepository extends JpaRepository<KeyUserInfo, Long> {

    List<KeyUserInfo> findByEmail(String email);

}
