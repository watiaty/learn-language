package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.Translation;

public interface TranslationService {
    Translation addWordTranslation(Translation translation);

    void incNumberOfUses(Translation translation);

    void decNumberOfUses(Translation translation);

    void delete(Long id);
}
