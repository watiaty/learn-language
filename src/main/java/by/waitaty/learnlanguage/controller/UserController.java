package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.dto.Mapper;
import by.waitaty.learnlanguage.dto.request.UserDtoRequest;
import by.waitaty.learnlanguage.dto.response.UserDtoResponse;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private UserServiceImpl userService;
    private Mapper mapper;

    @GetMapping(path = "/info")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<UserDtoResponse> getInfo(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        return ResponseEntity.ok(mapper.userToUserDtoResponse(user));
    }

    @PostMapping(path = "/save")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UserDtoRequest userDto, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        if (!userDto.getFirstName().isEmpty() || !userDto.getFirstName().equals(user.getFirstName())) user.setFirstName(userDto.getFirstName());
        if (!userDto.getLastName().isEmpty() || !userDto.getLastName().equals(user.getLastName())) user.setLastName(userDto.getLastName());
        if (!userDto.getEmail().isEmpty() || !userDto.getEmail().equals(user.getEmail())) user.setEmail(userDto.getEmail());
        if (userDto.getNativeLang() != user.getNativeLang()) user.setFirstName(userDto.getFirstName());
        // Update
//        if (userDto.getLearningLang() != user.getLearningLang()) user.setLearningLang(userDto.getLearningLang());
        return ResponseEntity.ok(mapper.userToUserDtoResponse(userService.updateUser(user)));
    }
}
