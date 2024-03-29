package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.repository.WordRepository;
import by.waitaty.learnlanguage.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    @Override
    public List<Word> findAllByLang(Language lang) {
        return wordRepository.findAllByLang(lang);
    }

    @Override
    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    @Override
    public Word addWord(Word word) {
        return wordRepository.save(word);
    }

    @Override
    public Word getWordByWordAndLang(String word, Language lang) {
        return wordRepository.findFirstByWordAndLang(word, lang);
    }

    @Override
    public Word findWordByName(String name) {
        return wordRepository.findByWord(name);
    }

    @Override
    public Word findWordById(Long id) {
        return wordRepository.findById(id).orElseThrow();
    }
}
