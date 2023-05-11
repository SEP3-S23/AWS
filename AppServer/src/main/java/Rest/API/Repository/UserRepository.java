package Rest.API.Repository;

import Rest.API.Model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
  User findByEmail(String email);

  default boolean register(User user){
    if(findByEmail(user.getEmail()) != null){
      return false;
    }

    String password = user.getPasswordHash();
    user.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));

    save(user);

    return true;
  }

  default User login(String email, String password)
  {
    User user = findByEmail(email);
    if(user==null)
    {
      return null;
    }

    if(BCrypt.checkpw(password,user.getPasswordHash())){
      return user;
    }
    return null;
  }
}
