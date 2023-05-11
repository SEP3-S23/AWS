package Rest.API.Service;

import Rest.API.Model.User;
import Rest.API.Repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
  @Autowired
  private UserRepository userRepository;

  public boolean register(User user)
  {
    if(userRepository.findByEmail(user.getEmail()) != null)
    {
      return false;
    }

    String password = user.getPasswordHash();
    user.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));

    userRepository.save(user);
    return true;
  }

  public User login(String email, String password)
  {
    User user = userRepository.findByEmail(email);
    if(user==null)
    {
      return null;
    }

    //The hashedPassword is different from the one stored in the database.
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    if(BCrypt.checkpw(password, user.getPasswordHash()))
    {
      System.out.println("Stored password hash: " + user.getPasswordHash());
      System.out.println("Expected password hash: " + hashedPassword);
      return user;
    } else
    {
      return null;
    }

  }
}
