using Shared.Model;

namespace Shared.DTOs;

public class RegisterDto
{
    public RegisterDto(string fullName, string email, DateTime? birthdate, string username, string password)
    {
        this.Password = password;
        this.BirthDate = birthdate;
        this.FullName = fullName;
        this.Email = email;
        this.UserName = username;
        Role = "USER";
    }

    public RegisterDto()
    {
    }

    public string FullName { get; set; }
    public string Email { get; set; }
    public DateTime? BirthDate { get; set; }
    public string UserName { get; set; }
    public string Password { get; set; }
    public string Role { get; set; } = "ADMIN";


    public string ToString()
    {
        return $"FullName: {FullName}" +
               $" Email: {Email}" +
               $" BirthdateBirthdate: {BirthDate}" +
               $" Username: {UserName}" +
               $"Password {Password}" +
               $"Role {Role}"
            ;
    }
}