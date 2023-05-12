package group3.app_server.Model;

import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.*;

@Entity
@Table(name="user_info", schema="app_info")
@Data
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column
  private String name;

  @Column
  private String surname;

  @Column
  private String username;

  @Column(name = "password", nullable = false)
  private String passwordHash;

  public void setPassword(String password) {
    this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.passwordHash);
  }
}
