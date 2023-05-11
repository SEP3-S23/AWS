package Rest.API.Model;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.*;

@Entity
@Table(name="user_info")
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
    String salt = BCrypt.gensalt();
    this.passwordHash = BCrypt.hashpw(password, salt);
  }

  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.passwordHash);
  }

  public String getPasswordHash()
  {
    return passwordHash;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getSurname()
  {
    return surname;
  }

  public void setSurname(String surname)
  {
    this.surname = surname;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  @Override public String toString()
  {
    return "User{" + "id=" + id + ", email='" + email + '\'' + ", name='" + name
        + '\'' + ", surname='" + surname + '\'' + ", username='" + username
        + '\'' + ", passwordHash='" + passwordHash + '\'' + '}';
  }
}
