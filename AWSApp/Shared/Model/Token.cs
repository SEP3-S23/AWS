namespace Shared.Model;

public class Token
{
    public Token(long id, string role, long ExpiresIn, string token)
    {
        this.id = id;
        this.role = role;
        this.token = token;
    }

    public long id { get; set; }
    
    public long ExpiresIn { get; set; }
    public string role { get; set; }
    public string token { get; set; }
    
    public bool IsExpired => DateTime.UtcNow > ExpiresAtUtc;
    public DateTime ExpiresAtUtc => DateTimeOffset.FromUnixTimeSeconds(ExpiresIn).UtcDateTime;
    

    public string ToString()
    {
        return $"id: {id}" +
               $" role: {role}" +
               $" token: {token}" +
               $" ExpiresIn: {ExpiresIn}"
              ;
    }
}