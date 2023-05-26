using System.Net;
using System.Net.Http.Json;
using Shared.DTOs;
using Shared.Model;
using Shared.Token;

namespace BackEnd.Services.Authentication;

public class AuthService : IAuthService
{
    private readonly string _baseUrl;
    private readonly HttpClient _httpClient;
    public string _jwtToken;

    public AuthService(string baseUrl)
    {
        _httpClient = new HttpClient();
        _baseUrl = baseUrl;
    }

    public async Task<string> LoginAsync(string userName, string password)
    {
        var loginDto = new LoginDto { userName = userName, password = password };

        var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/authenticate", loginDto);

        if (response.IsSuccessStatusCode)
        {
            var authenticationResponse = await response.Content.ReadFromJsonAsync<AuthenticationResponse>();
            _jwtToken = authenticationResponse.token;
            return _jwtToken;
        }

        if (response.StatusCode == HttpStatusCode.Unauthorized)
            throw new Exception("Unauthorized access");

        throw new Exception($"Request failed with status code {response.StatusCode}");
    }

    public string GetToken()
    {
        return _jwtToken;
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