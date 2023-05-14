package group3.app_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto
{
  private String email;
  private String name;
  private String surname;
  private String username;
  private String password;
}
