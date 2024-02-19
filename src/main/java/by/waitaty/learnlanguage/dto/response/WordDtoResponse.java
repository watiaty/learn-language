package by.waitaty.learnlanguage.dto.response;

import by.waitaty.learnlanguage.entity.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class WordDtoResponse {
    private Long id;
    private String word;
    private String transcription;
    private Set<Hashtag> hashtags;
    private List<String> translations;
}
