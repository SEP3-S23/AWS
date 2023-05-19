namespace Shared.DTOs;

public class LoginDto
{
    public LoginDto(string userName, string password)
    {
        userName = userName;
        password = password;
    }

    public LoginDto()
    {
    }

    public string userName { get; set; }
    public string password { get; set; }
}