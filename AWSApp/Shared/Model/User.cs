namespace Shared.Model;

public class User
{
    public User(long id, string email, string name, string surname, string username, string passwordHash, string token)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.passwordHash = passwordHash;
        this.token = token;
    }

    public long id { get; set; }
    public string email { get; set; }
    public string name { get; set; }
    public string surname { get; set; }
    public string username { get; set; }
    public string passwordHash { get; set; }
    public string token { get; set; }

    public string ToString()
    {
        return $"id: {id}" +
               $" email: {email}" +
               $" name: {name}" +
               $" surname: {surname}" +
               $" username: {username}" +
               $" passwordHash: {passwordHash}"+ 
               $" token: {token}";
    }
}