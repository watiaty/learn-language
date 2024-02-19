package by.waitaty.learnlanguage.repository;

import by.waitaty.learnlanguage.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

}
