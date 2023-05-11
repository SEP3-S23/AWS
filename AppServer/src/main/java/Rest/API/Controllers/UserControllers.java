package Rest.API.Controllers;

import Rest.API.Model.LoginDto;
import Rest.API.Model.User;
import Rest.API.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserControllers
{
  @Autowired
  UserService userService;

  @GetMapping("/")
  public String getPage(){
    return "welcome";
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user)
  {
    if(userService.register(user))
    {
      return new ResponseEntity<>(HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto)
  {
    User loggedInUser = userService.login(loginDto.getEmail(), loginDto.getPassword());
    if(loggedInUser != null)
    {
      return new ResponseEntity<>(loggedInUser,HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
