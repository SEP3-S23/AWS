using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using Backend.Services.Forums;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication
{
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

        public async Task CreateAsync(CreateForumDto dto)
        {
            try
            {
                var forum = new Forum
                {
                    Name = dto.ForumName,
                    Description = dto.ForumDescription,
                    Category = dto.ForumCategory
                };

               // var json = JsonSerializer.Serialize(forum);
                // var httpContent = new StringContent(json, Encoding.UTF8, "application/json");

                HttpResponseMessage response = await _httpClient.PostAsync($"{_baseUrl}/forums", httpContent);
                response.EnsureSuccessStatusCode();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Request failed: {ex.Message}");
                throw;
            }
        }
    }
}