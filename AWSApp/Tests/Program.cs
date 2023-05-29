using System.Text.Json;
using System.Text.Json.Serialization;
using BackEnd.Services.Authentication;
using Backend.Services.Forums;
using Shared.DTOs;

IAuthService authService = new AuthService("http://localhost:8080/api/v1/auth");
var token = await authService.LoginAsync("TestUsername3", "ExamplePassword");
Console.WriteLine(authService.GetToken());

IForumService forumService = new ForumService("http://localhost:8080/api/v1/forums", authService);
CreateForumDto dto = new CreateForumDto("DeeeASjjlig", "ligesom", "jeg");
var result = await forumService.CreateAsync(dto, authService.GetToken());
Console.WriteLine(result);
var flist = await  forumService.GetAllForumsAsync(); 
string json = JsonSerializer.Serialize(flist, new JsonSerializerOptions{WriteIndented = true});
Console.WriteLine(json);