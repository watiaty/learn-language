package by.waitaty.learnlanguage.dto.request;

import by.waitaty.learnlanguage.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddWordRequest {
    private String lang;
    private String word;
    private String[] translations;
    private String transcription;
}
