package by.waitaty.learnlanguage.dto.response;

import by.waitaty.learnlanguage.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class UserDtoResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String nativeLang;
    private List<String> learningLang;
    private Role role;
}
