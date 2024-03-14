package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.repository.TranslationRepository;
import by.waitaty.learnlanguage.service.TranslationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void decNumberOfUses(Translation translation) {
        translation.setNumberOfUses(translation.getNumberOfUses() - 1);
        translationRepository.save(translation);
    }

    @Override
    public void delete(Long id) {
        translationRepository.deleteById(id);
    }

    public Translation findById(Long id) {
        return translationRepository.findById(id).orElseThrow();
    }
}
