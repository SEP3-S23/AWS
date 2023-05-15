using BackEnd.Services.Authentication;
using Shared.DTOs;
using Shared.Model;

IAuthService authService = new AuthService();
//var result = await authService.LoginAsync("francesco@email.com", "ExamplePassword");
//Console.WriteLine(result.ToString());
/*RegisterDto exampleUser = new RegisterDto()
{
    email = "test3@email.com",
    fullName = "Full Name",
    passwordHash = "Password",
    username = "username3"
};
var registering = await authService.RegisterAsync(exampleUser);*/

var result2 = await authService.LoginAsync("username3", "Password");
Console.WriteLine(result2.ToString());