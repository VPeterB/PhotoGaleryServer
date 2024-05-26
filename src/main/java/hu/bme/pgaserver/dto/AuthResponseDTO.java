package hu.bme.pgaserver.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private Long id;
    private String accessToken;

    public AuthResponseDTO(Long id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }
}
