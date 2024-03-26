package by.waitaty.learnlanguage.dto;

import by.waitaty.learnlanguage.dto.response.TranslationSummaryDtoResponse;
import by.waitaty.learnlanguage.dto.response.UserDtoResponse;
import by.waitaty.learnlanguage.dto.response.UserWordDtoResponse;
import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.entity.UserWord;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {
    public UserWordDtoResponse userWordToWordDtoResponse(UserWord userWord) {
        return UserWordDtoResponse.builder()
                .id(userWord.getId())
                .word(userWord.getIdWord().toString())
                .translations(userWord.getTranslations().stream().map(Translation::getIdWord).toList())
                .build();
    }

    public TranslationSummaryDtoResponse wordTranslationSummaryDtoResponse(Translation translation, boolean contains) {
        return new TranslationSummaryDtoResponse(translation.getId(), translation.getIdTranslationWord(), contains, translation.getNumberOfUses());
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
}
