namespace Shared.DTOs;

public class CreatePostDto
{
    private string Username { get;}
    private DateTime Date { get;  }
    private string Body { get; }

    public CreatePostDto(string username, DateTime date, string body)
    {
        Username = username;
        Date = date;
        Body = body;
    }
}