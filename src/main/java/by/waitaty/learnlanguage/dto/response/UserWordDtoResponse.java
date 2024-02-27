package by.waitaty.learnlanguage.dto.response;

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
    private String lang;
    private String transcription;
    private boolean status;
    private List<String> translations;
}
