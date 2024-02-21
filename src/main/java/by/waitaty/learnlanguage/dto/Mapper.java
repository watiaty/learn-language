package by.waitaty.learnlanguage.dto;

import by.waitaty.learnlanguage.dto.response.TranslationSummaryDtoResponse;
import by.waitaty.learnlanguage.dto.response.UserDtoResponse;
import by.waitaty.learnlanguage.dto.response.UserWordDtoResponse;
import by.waitaty.learnlanguage.dto.response.WordDtoResponse;
import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.entity.Word;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {
    public UserWordDtoResponse userWordToWordDtoResponse(UserWord userWord) {
        return UserWordDtoResponse.builder()
                .id(userWord.getId())
                .word(userWord.getWord().getWord())
                .transcription(userWord.getWord().getTranscription())
                .translations(userWord.getTranslations().stream().map(Translation::getTranslation).toList())
                .build();
    }

    public WordDtoResponse wordToWordDtoResponse(Word word) {
        return WordDtoResponse.builder()
                .id(word.getId())
                .word(word.getWord())
                .hashtags(word.getHashtags())
                .transcription(word.getTranscription())
                .translations(word.getTranslations().stream().map(Translation::getTranslation).toList())
                .build();
    }

    public TranslationSummaryDtoResponse wordTranslationSummaryDtoResponse(Translation translation, boolean contains) {
        return new TranslationSummaryDtoResponse(translation.getId(), translation.getTranslation(), contains);
    }

    public UserDtoResponse userToUserDtoResponse(User user) {
        return UserDtoResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .nativeLang(user.getNativeLang().getId())
                .learningLang(user.getLearningLang().stream()
                        .map(Language::getId)
                        .collect(Collectors.toList()))
                .role(user.getRole())
                .build();
    }

//    public User toUser(UserCreationDTO userDTO) {
//        return new User(userDTO.getName(), userDTO.getPassword(), new ArrayList<>());
//    }
}
