package by.waitaty.learnlanguage.dto.request;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.StatusUserWord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordTrainDtoRequest {
    private int quantity;
    private StatusUserWord status;
    private String language;
}
