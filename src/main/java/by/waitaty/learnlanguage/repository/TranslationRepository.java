package by.waitaty.learnlanguage.repository;

import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Translation> findByTranslationAndWord(Word translation, Word word);
}
