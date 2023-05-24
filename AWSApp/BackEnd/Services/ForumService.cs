using System.Net;
using System.Net.Http.Json;
using System.Text;
using Backend.Services.Forums;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication;

public class ForumService : IForumService
{
    private readonly string _baseUrl;
    private readonly HttpClient _httpClient;

    public ForumService(string baseUrl)
    {
        _httpClient = new HttpClient();
        _baseUrl = baseUrl;
    }

    public async Task<Forum> CreateForum(string title, string description, string category)
    {
        // Prepare the data to send in the request body
        var requestData = new { Title = title, Description = description, Category= category };
    
        // Send the HTTP POST request to create the forum and deserialize the response
        HttpResponseMessage response = await _httpClient.PostAsJsonAsync("/api/forums", requestData);
    
        // Handle the response
        if (response.IsSuccessStatusCode)
        {
            // Forum creation successful
            // Deserialize the response content into a Forum object
            var createdForum = await response.Content.ReadFromJsonAsync<Forum>();
            return createdForum;
        }
        else
        {
            // Forum creation failed
            Console.WriteLine("Failed to create forum. Error: " + response.ReasonPhrase);
            return null;
        }
    }

    public Task<Forum> LoginAsync(string email, string password, string category)
    {
        throw new NotImplementedException();
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

    public List<Forum> GetAllForums()
    {
        throw new NotImplementedException();
    }
}