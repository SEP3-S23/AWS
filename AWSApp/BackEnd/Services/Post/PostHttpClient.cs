using System.Net.Http.Json;
using System.Text.Json;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication.Post;

public class PostHttpClient : IPostService
{
    private readonly HttpClient client;

    public PostHttpClient(HttpClient client)
    {
        this.client = client;
    }
    
    
    public async Task CreateAsync(CreatePostDto dto)
    {
        HttpResponseMessage response = await client.PostAsJsonAsync("/posts",dto);
        if (!response.IsSuccessStatusCode)
        {
            string content = await response.Content.ReadAsStringAsync();
            throw new Exception(content);
        }
    }
    
    public async Task<ICollection<Shared.Model.Post>> GetAsync()
    {
        HttpResponseMessage response = await client.GetAsync("/posts");
        string content = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(content);
        }

        ICollection<Shared.Model.Post> posts = JsonSerializer.Deserialize<ICollection<Shared.Model.Post>>(content, new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        })!;
        return posts;
    }
}