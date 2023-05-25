namespace Shared.DTOs;

public class CreatePostDto
{
    private string Title { get;}

    private string Username { get; }
    private string Body { get; }

    public CreatePostDto(string username, string title, string body)
    {
        Username = username;
        Title = title;
        Body = body;
    }
}