﻿namespace Shared.DTOs;

public class RegisterDto
{
    public string email { get; set; }
    public string name { get; set; }
    public string surname { get; set; }
    public string username { get; set; }
    public string password { get; set; }
    
    public RegisterDto()
    {
    }
    
}