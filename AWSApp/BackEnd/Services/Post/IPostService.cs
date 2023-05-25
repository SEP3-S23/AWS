using Shared.DTOs;


namespace BackEnd.Services.Authentication.Post;

public interface IPostService
{
    Task CreateAsync(CreatePostDto dto);
    
    Task<ICollection<Shared.Model.Post>> GetAsync();
}