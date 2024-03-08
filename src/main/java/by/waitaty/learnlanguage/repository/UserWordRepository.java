package by.waitaty.learnlanguage.repository;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.entity.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWordRepository extends JpaRepository<UserWord, Long> {
    @Modifying
    @Query(value = "insert into user_word (id_word, id_user) VALUES (:wordId, :userId)", nativeQuery = true)
    void add(@Param("wordId") Long wordId, @Param("userId") Long userId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"word", "translations"})
    List<UserWord> findAllByUserOrderByIdAsc(User user);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"word", "translations"})
    @Query("""
            SELECT uw FROM UserWord uw
            WHERE uw.user = :user\s
            AND uw.word.lang = :language\s
            AND (
                (uw.repeatStage = 1 AND uw.repeatDate != CURRENT_DATE())
                OR (uw.repeatStage = 2 AND uw.repeatDate < DATEADD(DAY, -7, CURRENT_DATE()))
                OR (uw.repeatStage = 3 AND uw.repeatDate < DATEADD(DAY, -30, CURRENT_DATE()))
            )
            ORDER BY uw.repeatStage DESC, uw.repeatDate ASC
            """)
    List<UserWord> findAllByUserAndWordLangOrderByRepeatStageDescRepeatDateAsc(User user, Language language, Pageable pageable);

    Optional<UserWord> findUserWordByWordAndUser(Word word, User user);

    @Modifying
    @Query(value = "DELETE FROM user_translation WHERE id_translation = :translationId AND id_user = :userId", nativeQuery = true)
    void deleteTranslationByTranslationAndUser(@Param("translationId") Long wordId, @Param("userId") Long userId);

    Optional<UserWord> findUSerWordByTranslationsContainsAndUser(Translation translation, User user);
}
