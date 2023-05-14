
using Shared.Model;

public interface IAuthService
{
    Task<User> LoginAsync(string email, string password);
    Task<string> GetPageAsync();
}