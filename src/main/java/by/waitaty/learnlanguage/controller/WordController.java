package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.dto.Mapper;
import by.waitaty.learnlanguage.dto.request.AddNewTranslationRequest;
import by.waitaty.learnlanguage.dto.request.AddWordRequest;
import by.waitaty.learnlanguage.dto.response.TranslationSummaryDtoResponse;
import by.waitaty.learnlanguage.dto.response.UserWordDtoResponse;
import by.waitaty.learnlanguage.dto.response.WordDtoResponse;
import by.waitaty.learnlanguage.dto.response.WordInfoDtoResponse;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.service.impl.TranslationServiceImpl;
import by.waitaty.learnlanguage.service.impl.UserServiceImpl;
import by.waitaty.learnlanguage.service.impl.UserWordServiceImpl;
import by.waitaty.learnlanguage.service.impl.WordServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/word")
@AllArgsConstructor
public class WordController {
    private final WordServiceImpl wordService;
    private final TranslationServiceImpl wordTranslationService;
    private final UserServiceImpl userService;
    private final UserWordServiceImpl userWordService;
    private Mapper mapper;

    @GetMapping(path = "/my")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public List<UserWordDtoResponse> getAll(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();

        return userWordService.getAllByWord(user).stream()
                .map(mapper::userWordToWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "/train")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public List<UserWordDtoResponse> getWordsForTraining(@RequestParam("count") int countWord, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<UserWord> userWords = userWordService.getCountWords(countWord, user);
        return userWords.stream()
                .map(mapper::userWordToWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "all")
    public List<WordDtoResponse> getAll() {
        return wordService.findAll()
                .stream()
                .map(mapper::wordToWordDtoResponse)
                .toList();
    }


    @GetMapping
    @SecurityRequirement(name = "JWT")
    public WordInfoDtoResponse getWord(@RequestParam("word") String name, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Word word = wordService.findWordByName(name);
        Optional<UserWord> userWord = userWordService.getUserWordByWord(word, user);
        List<TranslationSummaryDtoResponse> translationSummaryDtoResponses;
        if (userWord.isPresent()) {
            translationSummaryDtoResponses = word.getTranslations().stream()
                    .map(translation -> {
                        boolean contains = userWord.get().getTranslations().contains(translation);
                        return mapper.wordTranslationSummaryDtoResponse(translation, contains);
                    })
                    .toList();
        } else {
            translationSummaryDtoResponses = word.getTranslations().stream()
                    .map(translation -> mapper.wordTranslationSummaryDtoResponse(translation, false))
                    .toList();
        }

        return WordInfoDtoResponse.builder()
                .id(word.getId().toString())
                .word(word.getWord())
                .translations(translationSummaryDtoResponses)
                .transcription(word.getTranscription())
                .lang(word.getLang().getId())
                .build();
    }

    @Transactional
    @PostMapping("/translation/add")
    @SecurityRequirement(name = "JWT")
    public void addTranslationToUser(@RequestBody Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Translation translation = wordTranslationService.findById(id);
        Optional<UserWord> userWord = userWordService.getUserWordByWord(translation.getWord(), user);
        if (userWord.isEmpty()) {
            userWord = Optional.ofNullable(UserWord.builder()
                    .word(translation.getWord())
                    .user(user)
                    .isLearning(true)
                    .build());
        }

        userWord.get().addTranslation(translation);
        userWordService.update(userWord.get());
    }

    @Transactional
    @PostMapping("/translation/delete")
    @SecurityRequirement(name = "JWT")
    public void deleteTranslationToUser(@RequestBody Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Translation translation = wordTranslationService.findById(id);
        UserWord userWord = userWordService.getUserWordByTranslationAndUser(translation, user);
        userWord.deleteTranslation(translation);
        userWordService.update(userWord);
    }

    @Transactional
    @PostMapping("/delete")
    @SecurityRequirement(name = "JWT")
    public void deleteUserWord(@RequestBody Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        userWordService.delete(id);
    }

    @Transactional
    @PostMapping("/translation/fulldelete")
    @SecurityRequirement(name = "JWT")
    public void deleteTranslation(@RequestBody Long id) {
        wordTranslationService.delete(id);
    }

    @Transactional
    @PostMapping("/translation/new")
    @SecurityRequirement(name = "JWT")
    public void newTranslationToUser(@RequestBody AddNewTranslationRequest request, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Word word = wordService.findWordById(request.getId());
        Translation translation = wordTranslationService.addWordTranslation(
                Translation.builder()
                        .translation(request.getTranslation())
                        .word(word)
                        .user(user)
                        .numberOfUses(0L)
                        .lang(user.getNativeLang())
                        .build()
        );
        Optional<UserWord> userWord = userWordService.getUserWordByWord(word, user);
        if (userWord.isPresent()) {
            userWord.get().addTranslation(translation);
        } else {
            userWord = Optional.ofNullable(UserWord.builder()
                    .user(user)
                    .word(word)
                    .isLearning(true)
                    .build());
        }
        userWordService.update(userWord.get());
    }

    @PostMapping("/add")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<String> addWord(@RequestBody AddWordRequest addWordRequest, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Word word = wordService.addWord(Word.builder()
                .word(addWordRequest.getWord())
                .lang(addWordRequest.getLang())
                .transcription(addWordRequest.getTranscription())
                .build());
        Translation translation = wordTranslationService.addWordTranslation(
                Translation.builder()
                        .translation(addWordRequest.getTranslations())
                        .word(word)
                        .user(user)
                        .numberOfUses(0L)
                        .lang(user.getNativeLang())
                        .build()
        );
        UserWord userWord = UserWord.builder()
                .user(user)
                .word(word)
                .isLearning(true)
                .build();
        userWord.addTranslation(translation);
        userWordService.update(userWord);
        return ResponseEntity.ok("Word was successfully added");
    }
}
