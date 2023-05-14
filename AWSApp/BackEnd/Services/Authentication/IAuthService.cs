using System.Net.Http;
using System.Threading.Tasks;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication;

public interface IAuthService
{
    Task<User> LoginAsync(string email, string password);
    Task<HttpResponseMessage> RegisterAsync(RegisterDto user);
    Task<string> GetPageAsync();
}