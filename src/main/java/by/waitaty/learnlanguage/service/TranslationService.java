package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.Word;

import java.util.List;

public interface TranslationService {
    Translation addWordTranslation(Translation translation);

    void incNumberOfUses(Translation translation);

    List<Translation> getAllWordTranslationByWordAndLang(Word word, Language lang);

    List<Translation> findAll();

    List<Translation> getAllByWord(Word word);

    void delete(Long id);
}
