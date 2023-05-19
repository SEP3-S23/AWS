using System.Net;
using System.Net.Http.Json;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication;

public class AuthService : IAuthService
{
    private readonly string _baseUrl;
    private readonly HttpClient _httpClient;

    public AuthService(string baseUrl)
    {
        _httpClient = new HttpClient();
        _baseUrl = baseUrl;
    }

    public async Task<User> LoginAsync(string userName, string password)
    {
        var loginDto = new LoginDto { userName = userName, password = password };

        var response = await _httpClient.PostAsJsonAsync("http://localhost:8080/api/v1/auth/authenticate", loginDto);

        if (response.IsSuccessStatusCode)
        {
            Console.WriteLine(response.Content.ToString());
            var user = await response.Content.ReadFromJsonAsync<User>();
            return user;
        }

        if (response.StatusCode == HttpStatusCode.Unauthorized)
            throw new Exception("Unauthorized access");
        throw new Exception($"Request failed with status code {response.StatusCode}");
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