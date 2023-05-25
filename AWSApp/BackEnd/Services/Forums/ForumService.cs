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

    public  async Task CreateAsync(CreateForumDto dto)
    {
        var forum = new Forum
        {
           
        }
        
        HttpResponseMessage response = await _httpClient.PostAsJsonAsync("/forums", dto);
        if (!response.IsSuccessStatusCode)
        {
            string content = await response.Content.ReadAsStringAsync();
            throw new Exception(content);
        }
        
    }
}