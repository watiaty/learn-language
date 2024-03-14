package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.repository.WordRepository;
import by.waitaty.learnlanguage.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    @Override
    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    @Override
    public Word addWord(Word word) {
        return wordRepository.save(word);
    }

    @Override
    public Optional<Word> findWordByNameAndLang(String name, Language language) {
        return wordRepository.findByWordAndLang(name, language);
    }

    @Override
    public Word findOrCreateWord(String name, Language lang, String transcription) {
        Optional<Word> existingWordOptional = wordRepository.findFirstByWordAndLang(name, lang);
        return existingWordOptional.orElseGet(() -> wordRepository.save(Word.builder()
                .word(name)
                .lang(lang)
                .transcription(transcription)
                .build()));
    }

    @Override
    public Word findWordById(Long id) {
        return wordRepository.findById(id).orElseThrow();
    }

    public List<Word> searchWords(String searchText) {
        return wordRepository.findAllByWordStartingWith(searchText);
    }
}
