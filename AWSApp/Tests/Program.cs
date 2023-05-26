using BackEnd.Services.Authentication;
using Backend.Services.Forums;
using Shared.DTOs;

IAuthService authService = new AuthService("http://localhost:8080/api/v1/auth");
var token = await authService.LoginAsync("TestUsername3", "ExamplePassword");
Console.WriteLine(token); 

IForumService forumService = new ForumService("http://localhost:8080/api/v1/forums");
CreateForumDto dto = new CreateForumDto("WORKING", "ihhs", "dujjmb");
var result = await forumService.CreateAsync(dto, token.ToString());
Console.WriteLine(result);