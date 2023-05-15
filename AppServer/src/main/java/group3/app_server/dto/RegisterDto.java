package group3.app_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto
{
  private String email;
  private String fullName;
  private String username;
  private String passwordHash;
}
