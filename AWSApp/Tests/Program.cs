using BackEnd.Services.Authentication;
using Backend.Services.Forums;
using Shared.DTOs;

/*IAuthService authService = new AuthService("http://localhost:8090/api/user");
var result = await authService.LoginAsync("francesco@email.com", "ExamplePassword");
Console.WriteLine(result.ToString()); */

IForumService forumService = new ForumService("http://localhost:8080/api/v1/forums");
CreateForumDto dto = new CreateForumDto("martaisbeautiful", "ihhs", "dujjmb");
var result = await forumService.CreateAsync(dto, "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInVzZXJJZCI6MSwic3ViIjoiVGVzdFVzZXJuYW1lMyIsImlhdCI6MTY4NTA1MDk3NSwiZXhwIjoxNjg1MDUyNDE1fQ.o6fRh6Ad-iFgbMkw2BMa6MBhPZtRbpBmUwPLjqd_YOU");
Console.WriteLine(result);