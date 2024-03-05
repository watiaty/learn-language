package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.StatusUserWord;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.repository.UserWordRepository;
import by.waitaty.learnlanguage.service.UserWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWordServiceImpl implements UserWordService {
    private final UserWordRepository userWordRepository;

    @Override
    public void addUserWord(UserWord userWord) {
        userWordRepository.save(userWord);
    }


    public void addWordToUser(Long id, Long userId) {
        userWordRepository.add(id, userId);
    }

    public List<UserWord> getAllByWord(User user) {
        return userWordRepository.findAllByUserOrderByIdAsc(user);
    }

    public List<UserWord> getCountLearningWords(int count, StatusUserWord statusUserWord, Language language, User user) {
        Pageable pageable = PageRequest.of(0, count);
        if (statusUserWord == StatusUserWord.LEARNING) {
            return userWordRepository.findAllByUserAndIsLearningAndWordLangOrderByDateAsc(user, true, language, pageable);
        }
        if (statusUserWord == StatusUserWord.LEARNED) {
            return userWordRepository.findAllByUserAndIsLearningAndWordLangOrderByDateAsc(user, false, language, pageable);
        }
        return userWordRepository.findAllByUserAndWordLangOrderByIsLearningDescDateAsc(user, language, pageable);
    }

    public Optional<UserWord> getUserWordByWord(Word word, User user) {
        return userWordRepository.findUserWordByWordAndUser(word, user);
    }

    public void deleteTranslationToUser(Long id, Long userId) {
        userWordRepository.deleteTranslationByTranslationAndUser(id, userId);
    }

    public UserWord update(UserWord userWord) {
        return userWordRepository.save(userWord);
    }

    public UserWord getUserWordByTranslationAndUser(Translation translation, User user) {
        return userWordRepository.findUSerWordByTranslationsContainsAndUser(translation, user);
    }

    public void delete(Long id) {
        userWordRepository.deleteById(id);
    }

    public Optional<UserWord> findById(Long id) {
        return userWordRepository.findById(id);
    }
}
