package mainFiles.Data;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainFiles.objects.Post;

@Repository
public interface PostData extends JpaRepository<Post, Integer> { }

