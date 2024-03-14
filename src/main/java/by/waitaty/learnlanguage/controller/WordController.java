package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.dto.Mapper;
import by.waitaty.learnlanguage.dto.request.AddNewTranslationRequest;
import by.waitaty.learnlanguage.dto.request.AddWordRequest;
import by.waitaty.learnlanguage.dto.request.WordTrainDtoRequest;
import by.waitaty.learnlanguage.dto.response.TranslationSummaryDtoResponse;
import by.waitaty.learnlanguage.dto.response.UserWordDtoResponse;
import by.waitaty.learnlanguage.dto.response.WordDtoResponse;
import by.waitaty.learnlanguage.dto.response.WordInfoDtoResponse;
import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.exception.WordNotFoundException;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @PostMapping(path = "/train")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public List<UserWordDtoResponse> getWordsForTraining(@RequestBody WordTrainDtoRequest wordTrainDtoRequest, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<UserWord> userWords = userWordService.getCountLearningWords(
                wordTrainDtoRequest.getQuantity(),
                Language.getLanguageFromString(wordTrainDtoRequest.getLanguage()), user
        );
        return userWords.stream()
                .map(mapper::userWordToWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "all")
    public List<WordDtoResponse> getAll() {
        return wordService.findAll().stream()
                .map(mapper::wordToWordDtoResponse)
                .toList();
    }

    @PostMapping("/update/learned")
    public void setLearnedStatusWord(@RequestBody Long id) {
        userWordService.findById(id).ifPresent(userWord -> {
            userWord.setRepeatDate(LocalDate.now());

            if (userWord.getRepeatStage() < 5) {
                userWord.setRepeatStage(userWord.getRepeatStage() + 1);
            }

            userWordService.update(userWord);
        });
    }

    @PostMapping("/update/learning")
    public void setLearningStatusWord(@RequestBody Long id) {
        userWordService.findById(id).ifPresent(userWord -> {
            userWord.setRepeatStage(1);
            userWord.setRepeatDate(LocalDate.now());
            userWordService.update(userWord);
        });
    }

    @GetMapping("/search")
    public List<WordInfoDtoResponse> search(@RequestParam("word") String searchText) {
        return wordService.searchWords(searchText).stream()
                .map(word -> WordInfoDtoResponse.builder()
                        .id(word.getId().toString())
                        .word(word.getWord())
                        .translations(word.getTranslations().stream()
                                .map(translation -> mapper.wordTranslationSummaryDtoResponse(translation, false))
                                .toList())
                        .transcription(word.getTranscription())
                        .language(word.getLang().getId())
                        .build())
                .toList();
    }

    @GetMapping
    @SecurityRequirement(name = "JWT")
    public WordInfoDtoResponse getWord(@RequestParam("word") String name, @RequestParam("lang") String lang, Principal principal) {
        Word word = wordService.findWordByNameAndLang(name, Language.getLanguageFromString(lang.toLowerCase())).orElseThrow(() -> new WordNotFoundException(name));
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<TranslationSummaryDtoResponse> translationSummaryDtoResponses = word.getTranslations().stream()
                .map(translation -> {
                    boolean contains = userWordService.getUserWordByTranslationAndUser(translation, user).isPresent();
                    return mapper.wordTranslationSummaryDtoResponse(translation, contains);
                })
                .toList();

        return WordInfoDtoResponse.builder()
                .id(word.getId().toString())
                .word(word.getWord())
                .translations(translationSummaryDtoResponses)
                .transcription(word.getTranscription())
                .language(word.getLang().getId())
                .build();
    }

    @Transactional
    @PostMapping("/translation/add")
    @SecurityRequirement(name = "JWT")
    public void addTranslationToUser(@RequestBody Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Translation translation = wordTranslationService.findById(id);
        UserWord userWord = userWordService.getUserWordByWord(translation.getWord(), user)
                .orElse(UserWord.builder()
                        .word(translation.getWord())
                        .user(user)
                        .repeatStage(1)
                        .repeatDate(LocalDate.now())
                        .build());

        userWord.addTranslation(translation);
        wordTranslationService.incNumberOfUses(translation);
        userWordService.update(userWord);
    }

    @Transactional
    @PostMapping("/translation/delete")
    @SecurityRequirement(name = "JWT")
    public void deleteTranslationToUser(@RequestBody Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Translation translation = wordTranslationService.findById(id);
        wordTranslationService.decNumberOfUses(translation);

        userWordService.getUserWordByTranslationAndUser(translation, user).ifPresent(userWord -> {
            userWord.deleteTranslation(translation);
            if (userWord.getTranslations().isEmpty()) {
                userWordService.delete(userWord.getId());
            } else {
                userWordService.update(userWord);
            }
        });
    }


    @Transactional
    @PostMapping("/delete")
    @SecurityRequirement(name = "JWT")
    public void deleteUserWord(@RequestBody Long id) {
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
        Word translationWord = Word.builder()
                .word(request.getTranslation())
                .lang(user.getNativeLang())
                .build();
        Translation translation = wordTranslationService.addWordTranslation(
                Translation.builder()
                        .word(word)
                        .translation(wordService.addWord(translationWord))
                        .numberOfUses(0L)
                        .build()
        );

        UserWord userWord = userWordService.getUserWordByWord(word, user)
                .orElseGet(() -> UserWord.builder()
                        .user(user)
                        .word(word)
                        .repeatStage(1)
                        .repeatDate(LocalDate.now())
                        .build());

        userWord.addTranslation(translation);
        userWordService.update(userWord);
    }

    @PostMapping("/add")
    @SecurityRequirement(name = "JWT")
    @Transactional
    public ResponseEntity<UserWordDtoResponse> addWord(@RequestBody AddWordRequest addWordRequest, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Word word = wordService.findOrCreateWord(addWordRequest.getWord(), Language.getLanguageFromString(addWordRequest.getLang()), addWordRequest.getTranscription());
        UserWord userWord = createUserWord(user, word, Arrays.stream(addWordRequest.getTranslations()).toList());
        UserWord savedUserWord = userWordService.update(userWord);
        return ResponseEntity.ok(mapper.userWordToWordDtoResponse(savedUserWord));
    }

    private UserWord createUserWord(User user, Word word, List<String> translations) {
        UserWord userWord = UserWord.builder()
                .user(user)
                .word(word)
                .repeatStage(1)
                .repeatDate(LocalDate.now())
                .build();
        translations.forEach(translation -> {
            Word translationWord = wordService.findOrCreateWord(translation, user.getNativeLang(), "");
            Translation newTranslated = wordTranslationService.addWordTranslation(
                    Translation.builder()
                            .translation(wordService.addWord(translationWord))
                            .word(word)
                            .numberOfUses(1L)
                            .build()
            );
            userWord.addTranslation(newTranslated);
        });
        return userWord;
    }
}
