package by.waitaty.learnlanguage.repository;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Word;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByLang(Language lang);

    Word findFirstByWordAndLang(String word, Language lang);

    Word findByWord(String name);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"hashtags", "translations"})
    List<Word> findAllByWordStartingWithAndAndLang(String searchText, Language language);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"hashtags", "translations"})
    List<Word> findAll();
}
