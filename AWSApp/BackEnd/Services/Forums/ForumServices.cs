using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;
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
        private IForumService _forumServiceImplementation;
        private string JwtToken;

        public ForumService(string baseUrl)
        {
            _httpClient = new HttpClient();
            _baseUrl = baseUrl;
            JwtToken = LocalToken.Instance.token;
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

        public async Task <List<ForumListDto>> GetAllForumsAsync() 
        {
            List<ForumListDto> forums = null;
            
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", JwtToken);
            
            try
            {
                HttpResponseMessage response = await _httpClient.GetAsync($"{_baseUrl}");
                if (response.IsSuccessStatusCode)
                {
                    forums = await response.Content.ReadFromJsonAsync<List<ForumListDto>>();
                }
                else
                {
                    Console.WriteLine($"AN errror occured");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }

            return forums;
        }

        public async Task<string> CreateAsync(CreateForumDto forumRequest)
        {
            
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", JwtToken);
            var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/create", forumRequest);
            if (response.IsSuccessStatusCode)
            {
                return await response.Content.ReadAsStringAsync();
            }
            if (response.StatusCode == HttpStatusCode.Conflict)
            {
                return "The forum name already exists. Please choose another name. miau miau.";
            }
            if (response.StatusCode == HttpStatusCode.Unauthorized)
            {
                return "Your session has expired. Please login again. woof woof.";
            }
            throw new Exception($"Request failed with status code {response.StatusCode}");
        }
    }
        
       /* public async Task CreateAsync(CreateForumDto dto)
        {
            try
            {
               
                var json = JsonSerializer.Serialize(forum);
                var httpContent = new StringContent(json, Encoding.UTF8, "application/json");

                HttpResponseMessage response = await _httpClient.PostAsync($"{_baseUrl}/forums", httpContent);
                response.EnsureSuccessStatusCode();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Request failed: {ex.Message}");
                throw;
            }
        }*/
}