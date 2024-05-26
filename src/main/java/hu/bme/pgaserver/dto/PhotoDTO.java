package hu.bme.pgaserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class PhotoDTO {
    private Long id;
    private String filename;
    private String name;
    private LocalDateTime creationDate;
}

