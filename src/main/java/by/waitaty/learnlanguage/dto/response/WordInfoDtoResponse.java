package by.waitaty.learnlanguage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class WordInfoDtoResponse {
    private String id;
    private String word;
    private String transcription;
    private String language;
    private List<TranslationSummaryDtoResponse> translations;
}
