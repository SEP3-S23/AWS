using BackEnd.Services.Authentication;

IAuthService authService = new AuthService("http://localhost:8090/api/user");
var result = await authService.LoginAsync("francesco@email.com", "ExamplePassword");
Console.WriteLine(result.ToString());