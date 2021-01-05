package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
