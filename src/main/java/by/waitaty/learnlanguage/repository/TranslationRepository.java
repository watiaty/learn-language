package by.waitaty.learnlanguage.repository;

import by.waitaty.learnlanguage.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

}
