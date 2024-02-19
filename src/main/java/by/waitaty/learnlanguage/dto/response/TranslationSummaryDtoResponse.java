package by.waitaty.learnlanguage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TranslationSummaryDtoResponse {
    private Long id;
    private String translation;
    private boolean isUser;
}
