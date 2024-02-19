package by.waitaty.learnlanguage.repository;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findAllByWord(Word word);

    List<Translation> findAllByWordAndLang(Word word, Language lang);

    @Query("SELECT t FROM Translation t LEFT JOIN FETCH t.word")
    List<Translation> findAllWithWord();
}
