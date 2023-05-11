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

    if(BCrypt.checkpw(password, user.getPasswordHash()))
    {
      return user;
    } else
    {
      return null;
    }

  }
}
