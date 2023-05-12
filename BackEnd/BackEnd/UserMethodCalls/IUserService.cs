namespace BackEnd.UserMethodCalls;

public interface IUserService
{
    Task<string> Login(string username, string password);
}