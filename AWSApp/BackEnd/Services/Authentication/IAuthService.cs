using Shared.Model;

namespace BackEnd.Services.Authentication;

public interface IAuthService
{
    Task<Token> LoginAsync(string email, string password);
    Task<Token> RegisterAsync(string fullName, string email, DateTime? birthdate, string username, string password);
    
    Task<string> GetPageAsync();
}