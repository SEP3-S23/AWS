using System;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text;
using System.Threading.Tasks;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication;

public class AuthService : IAuthService
{
    private readonly string _baseUrl;
    private readonly HttpClient _httpClient;

    public AuthService()
    {
        _httpClient = new HttpClient();
        _baseUrl = "http://localhost:8090/api/user";
    }

    public async Task<HttpResponseMessage> RegisterAsync(RegisterDto user)
    {

        var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/register", user);

        if (response.StatusCode == HttpStatusCode.Created)
        {
            return response;
        }

        if (response.StatusCode == HttpStatusCode.Conflict)
        {
            throw new Exception($"Not possible to register. Request failed with status code {response.StatusCode}");
        }

        return response;
    }
    
    public async Task<User> LoginAsync(string email, string password)
    {
        var loginDto = new LoginDto { Email = email, Password = password };

        var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/login", loginDto);

        if (response.IsSuccessStatusCode)
        {
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