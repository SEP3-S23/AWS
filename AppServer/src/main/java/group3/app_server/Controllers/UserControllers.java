package group3.app_server.Controllers;

import group3.app_server.dto.LoginDto;
import group3.app_server.Model.User;
import group3.app_server.Service.UserService;
import group3.app_server.dto.RegisterDto;
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
  public ResponseEntity<?> register(@RequestBody RegisterDto user)
  {
    User registerUser = new User(user.getEmail(), user.getFullName(),user.getUsername(),
        user.getPasswordHash());
    if(userService.register(registerUser))
    {
      return new ResponseEntity<>(HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto)
  {
    User loggedInUser = userService.login(loginDto.getUsername(), loginDto.getPassword());
    if(loggedInUser != null)
    {
      return new ResponseEntity<>(loggedInUser,HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
