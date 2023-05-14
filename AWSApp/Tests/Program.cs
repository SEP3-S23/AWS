using BackEnd.Services.Authentication;
using Shared.DTOs;
using Shared.Model;

IAuthService authService = new AuthService();
//var result = await authService.LoginAsync("francesco@email.com", "ExamplePassword");
//Console.WriteLine(result.ToString());
/*RegisterDto exampleUser = new RegisterDto()
{
    email = "test@email.com",
    name = "Name",
    surname = "Surname",
    password = "Password",
    username = "username"
};
var registering = await authService.RegisterAsync(exampleUser);*/

var result2 = await authService.LoginAsync("test@email.com", "Password");
Console.WriteLine(result2.ToString());