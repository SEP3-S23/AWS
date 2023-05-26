using Shared.Model;
using Shared.Token;

namespace BackEnd.Services.Authentication;

public interface IAuthService
{
    Task<string> LoginAsync(string username, string password);
    string GetToken();
    Task<string> GetPageAsync();
}