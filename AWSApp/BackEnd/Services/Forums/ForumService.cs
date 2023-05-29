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
        private IAuthService authservice;
        private string JwtToken;

        public ForumService(string baseUrl, IAuthService authService)
        {
            _httpClient = new HttpClient();
            _baseUrl = baseUrl;
            authservice = authService;
            JwtToken = authservice.GetToken();
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

        public async Task<string> CreateAsync(CreateForumDto forumRequest,string token)
        {
            
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/create", forumRequest);

            if (response.IsSuccessStatusCode)
            {
                return await response.Content.ReadAsStringAsync();
            }
            else if (response.StatusCode == HttpStatusCode.Conflict)
            {
                return "The forum name already exists. Please choose another name. miau miau.";
            }
            else if (response.StatusCode == HttpStatusCode.Unauthorized)
            {
                return "Your session has expired. Please login again. woof woof.";
            }
            else
            {
                // Handle other status codes accordingly
                return "An error occurred.";
            }
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