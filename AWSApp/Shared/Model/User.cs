namespace Shared.Model;

public class User
{
    public User(string email, string username, string fullName, string passwordHash)
    {
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
    }

    public long id { get; set; }
    public string email { get; set; }
    public string username { get; set; }
    public string fullName { get; set; }
    public string passwordHash { get; set; }

    public string ToString()
    {
        return $"id: {id}" +
               $" email: {email}" +
               $"username: {username}" +
               $" full name: {fullName}" +
               $" password hash: {passwordHash}";
    }
}