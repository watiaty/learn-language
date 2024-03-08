package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.Language;
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

    public List<UserWord> getAllByWord(User user) {
        return userWordRepository.findAllByUserOrderByIdAsc(user);
    }

    public List<UserWord> getCountLearningWords(int count, Language language, User user) {
        if (count == 0) count = Integer.MAX_VALUE;
        Pageable pageable = PageRequest.of(0, count);
        return userWordRepository.findAllByUserAndWordLangOrderByRepeatStageDescRepeatDateAsc(user, language, pageable);
    }

    public Optional<UserWord> getUserWordByWord(Word word, User user) {
        return userWordRepository.findUserWordByWordAndUser(word, user);
    }

    public UserWord update(UserWord userWord) {
        return userWordRepository.save(userWord);
    }

    public Optional<UserWord> getUserWordByTranslationAndUser(Translation translation, User user) {
        return userWordRepository.findUSerWordByTranslationsContainsAndUser(translation, user);
    }

    public void delete(Long id) {
        userWordRepository.deleteById(id);
    }

    public Optional<UserWord> findById(Long id) {
        return userWordRepository.findById(id);
    }
}
