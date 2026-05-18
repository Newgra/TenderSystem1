package com.example.tendersystem.resourcess;

import com.example.tendersystem.model.User;
import com.example.tendersystem.service.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    private final UserService userService = new UserService();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User loginData) {
        // Шукаємо користувача в базі
        User dbUser = userService.authenticateUser(loginData.getLogin(), loginData.getPassword());

        if (dbUser != null) {
            // ОБОВ'ЯЗКОВО: відправляємо знайденого користувача назад (dbUser)
            return Response.ok(dbUser).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Невірний логін або пароль\"}")
                    .build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        boolean success = userService.registerUser(user.getUsername(), user.getLogin(), user.getPassword());

        if (success) {
            return Response.ok("{\"message\": \"Успішна реєстрація!\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Помилка! Можливо, такий логін вже зайнятий.\"}")
                    .build();
        }
    }
}