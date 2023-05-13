using BackEnd.UserMethodCalls;

IUserService userService = new UserService();
Console.WriteLine(userService.Login("francesco@email.com", "PasswordTest"));