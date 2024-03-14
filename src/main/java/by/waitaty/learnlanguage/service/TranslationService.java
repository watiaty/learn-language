package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.Word;

public interface TranslationService {
    Translation addWordTranslation(Word translation, Word word);

    void incNumberOfUses(Translation translation);

    void decNumberOfUses(Translation translation);

    void delete(Long id);
}
