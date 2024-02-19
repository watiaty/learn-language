package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.repository.TranslationRepository;
import by.waitaty.learnlanguage.service.TranslationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final TranslationRepository translationRepository;

    @Override
    public Translation addWordTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    @Override
    public void incNumberOfUses(Translation translation) {
        translation.setNumberOfUses(translation.getNumberOfUses() + 1);
        translationRepository.save(translation);
    }

    @Override
    public List<Translation> getAllWordTranslationByWordAndLang(Word word, Language lang) {
        return translationRepository.findAllByWordAndLang(word, lang);
    }

    @Override
    public List<Translation> findAll() {
        return translationRepository.findAllWithWord();
    }

    @Override
    public List<Translation> getAllByWord(Word word) {
        return translationRepository.findAllByWord(word);
    }

    @Override
    public void delete(Long id) {
        translationRepository.deleteById(id);
    }

    public Translation findById(Long id) {
        return translationRepository.findById(id).orElseThrow();
    }
}
