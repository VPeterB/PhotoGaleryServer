package hu.bme.pgaserver.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private Timestamp entryDate;
    private List<String> roles;
}
