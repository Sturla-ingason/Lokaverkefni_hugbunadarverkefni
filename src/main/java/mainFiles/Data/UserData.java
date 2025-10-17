package mainFiles.Data;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import jakarta.transaction.Transactional;
import mainFiles.objects.User;
//import java.util.List;



@Repository
public interface UserData extends JpaRepository<User, Integer> {

    User save(User user);

    void delete(User user);

    //@Modifying
    //@Transactional
    //@Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    //int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);

    User findByEmail(String email);
}
