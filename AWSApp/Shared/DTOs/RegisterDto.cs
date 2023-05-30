using Shared.Model;

namespace Shared.DTOs;

public class RegisterDto
{
    public RegisterDto(string fullName, string email, DateTime? birthdate, string username, string password)
    {
        this.Password = password;
        this.Birthdate = birthdate;
        this.FullName = fullName;
        this.Email = email;
        this.Username = username;
        Role = "ADMIN";
    }

    public RegisterDto()
    {
    }

    public string FullName { get; set; }
    public string Email { get; set; }
    public DateTime? Birthdate { get; set; }
    public string Username { get; set; }
    public string Password { get; set; }
    public string Role { get; set; } = "ADMIN";


    public string ToString()
    {
        return $"FullName: {FullName}" +
               $" Email: {Email}" +
               $" BirthdateBirthdate: {Birthdate}" +
               $" Username: {Username}" +
               $"Password {Password}" +
               $"Role {Role}"
            ;
    }
}