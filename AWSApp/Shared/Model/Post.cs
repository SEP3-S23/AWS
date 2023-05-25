namespace Shared.Model;

public class Post
{
    public int Id { get; set; }
    public User Username { get; }
    public string Title { get; }

    public string Body { get; set; }

    public Post(User username, string title, string body)
    {
        Username = username;
        Title = title;
        Body = body;

    }
}