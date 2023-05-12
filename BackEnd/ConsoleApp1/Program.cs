using BackEnd.UserMethodCalls;

IUserService userService = new UserService();
userService.Login("francesco@email.com", "PasswordTest");