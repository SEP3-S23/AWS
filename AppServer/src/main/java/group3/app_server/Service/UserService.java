package group3.app_server.Service;

import group3.app_server.Model.User;
import group3.app_server.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService
{
  @Autowired
  private UserRepository userRepository;

  public boolean register(User user)
  {
    if(userRepository.findByEmail(user.getEmail()) != null && userRepository.findByUsername(user.getUsername()) != null)
    {
      return false;
    }
    user.setPassword(user.getPasswordHash());
    userRepository.save(user);
    return true;
  }

  public User login(String username, String password)
  {
    User user = userRepository.findByUsername(username);
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
