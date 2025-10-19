package mainFiles.Data;

import jakarta.persistence.EntityManager;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowerDataImp implements FollowerData {
    private EntityManager entityManager;

    @Autowired
    private UserData userData;

    @Override
    public void followUser(Integer userID, Integer followID) {
        String sql = """
            INSERT INTO user_follows (userID, followID)
            VALUES (:userID, :followId)
            ON CONFLICT DO NOTHING
        """;
        entityManager.createNativeQuery(sql)
                .setParameter("userID", userID)
                .setParameter("followId", followID)
                .executeUpdate();
    }

    @Override
    public void unfollowUser(Integer userID, Integer followID) {
        String sql = """
            DELETE FROM user_follows
            WHERE userID = :userID AND followID = :followID
        """;
        entityManager.createNativeQuery(sql)
                .setParameter("userID", userID)
                .setParameter("followId", followID)
                .executeUpdate();
    }

    @Override
    public List getAllFollowedByUser(Integer userID) {
        String sql = """
            SELECT u.*
            FROM users u
            JOIN user_follows f ON f.followed_id = u.id
            WHERE f.follower_id = :userID
        """;
        return entityManager.createNativeQuery(sql, User.class)
                .setParameter("userID", userID)
                .getResultList();
    }

    @Override
    public Integer getAmountOfFollowers(Integer userID) {
        String sql = """
            SELECT COUNT(*) FROM user_follows WHERE followed_id = :userId""";

        Object count = entityManager.createNativeQuery(sql)
                .setParameter("userID", userID)
                .getSingleResult();
        return ((Number) count).intValue();
    }
}
