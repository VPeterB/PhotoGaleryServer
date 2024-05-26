package hu.bme.pgaserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    private String name;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 40, message = "Username must be between 3 and 40 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters")
    private String password;
    @NotBlank(message = "Role is required")
    @Size(min = 3, max = 20, message = "Role must be between 3 and 20 characters")
    private String role;
}
