package org.example.backend.requests;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequest {
    @NotNull(message = "username cannot be null!")
    @Size(min=1)
    private String username;

    @NotNull
    @Size(min=1)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
