﻿using System.Net;
using System.Net.Http.Json;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication;


public class AuthService : IAuthService
{
    private readonly string _baseUrl;
    private readonly HttpClient _httpClient;
    private Token _jwtToken;

    public AuthService(string baseUrl)
    {
        _httpClient = new HttpClient();
        _baseUrl = baseUrl;
    }

    public async Task<Token> LoginAsync(string userName, string password)
    {
        var loginDto = new LoginDto { userName = userName, password = password };

        var response = await _httpClient.PostAsJsonAsync("http://localhost:8080/api/v1/auth/authenticate", loginDto);

        if (response.IsSuccessStatusCode)
        {
            Console.WriteLine(response.Content.ToString());
            _jwtToken = await response.Content.ReadFromJsonAsync<Token>();
            LocalToken.Instance.token = _jwtToken.token;
            return _jwtToken;
        }

        if (response.StatusCode == HttpStatusCode.Unauthorized)
            throw new Exception("Unauthorized access");
        throw new Exception($"Request failed with status code {response.StatusCode}");
    }

    public async Task<Token> RegisterAsync(string fullName, string email, DateTime? birthdate, string username, string password)
    {
        
        var registerDto = new RegisterDto { FullName = fullName, Email = email, BirthDate = birthdate, UserName = username, Password = password};
        var response = await _httpClient.PostAsJsonAsync("http://localhost:8080/api/v1/auth/register", registerDto);

        if (response.IsSuccessStatusCode)
        {
            var _jwtToken = await response.Content.ReadFromJsonAsync<Token>();
            LocalToken.Instance.token = _jwtToken.token;
            return _jwtToken;
        }

        if (response.StatusCode == HttpStatusCode.Unauthorized)
            throw new Exception("Unauthorized access");
        throw new Exception($"Request failed with status code {response.StatusCode} info: {response}");
    }

    public async Task<string> GetPageAsync()
    {
        try
        {
            var response = await _httpClient.GetAsync($"{_baseUrl}/");
            response.EnsureSuccessStatusCode();

            var content = await response.Content.ReadAsStringAsync();
            return content;
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Request failed: {ex.Message}");
            throw;
        }
    }
    
}