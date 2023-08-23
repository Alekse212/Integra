package com.example.Integra.controller;

import com.example.Integra.models.User;
import com.example.Integra.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;


@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;


    @GetMapping("/user_info")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        // Извлекаем токен из заголовка Authorization
        String token = authorizationHeader.replace("Bearer ", "");

        // Получаем информацию о пользователе из токена
        User user = userService.getUserFromToken(token);

        // Возвращаем информацию о пользователе в ответе
        var userInfo = User.builder().name(user.getName()).lastname(user.getLastname()).role(user.getRole()).build();
        return ResponseEntity.ok(userInfo);
    }

    public ResponseEntity<String> deleteUser(@RequestPart String email){
//        userService.delete(email);
        return ResponseEntity.ok("Deleted");
    }

}
