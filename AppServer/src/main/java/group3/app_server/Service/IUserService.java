package group3.app_server.Service;

import group3.app_server.Model.User;
import group3.app_server.Model.dto.LoginDto;
import group3.app_server.Model.dto.RegisterDto;

public interface IUserService
{
  User register(RegisterDto registerDto);
  String login(LoginDto loginDto);
}
