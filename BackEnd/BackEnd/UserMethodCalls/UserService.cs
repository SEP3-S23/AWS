using System;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;
using BackEnd.Dtos;
using BackEnd.Model;
using BackEnd.UserMethodCalls;

public class UserService : IUserService
{
    private readonly HttpClient _httpClient;
    private readonly string _baseUrl;

    public UserService(string baseUrl)
    {
        _httpClient = new HttpClient();
        _baseUrl = baseUrl;
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

        if (response.StatusCode == System.Net.HttpStatusCode.Unauthorized)
        {
            throw new Exception("Unauthorized access");
        }
        else
        {
            throw new Exception($"Request failed with status code {response.StatusCode}");
        }
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