package by.waitaty.learnlanguage.dto.response;

import by.waitaty.learnlanguage.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserWordDtoResponse {
    private Long id;
    private String word;
    private Language lang;
    private String transcription;
    private List<String> translations;
}
