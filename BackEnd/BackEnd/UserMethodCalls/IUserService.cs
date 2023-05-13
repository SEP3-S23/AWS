using BackEnd.Dtos;
using BackEnd.Model;

namespace BackEnd.UserMethodCalls;

public interface IUserService
{
    Task<User> LoginAsync(string email, string password);
    Task<string> GetPageAsync();
}