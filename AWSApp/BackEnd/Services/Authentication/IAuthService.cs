using Shared.Model;

namespace BackEnd.Services.Authentication;

public interface IAuthService
{
    Task<User> LoginAsync(string email, string password);
    Task<string> GetPageAsync();
}