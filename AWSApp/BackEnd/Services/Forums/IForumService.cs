using Shared.DTOs;
using Shared.Model;

namespace Backend.Services.Forums;

public interface IForumService
{
    
    Task<string> CreateAsync(CreateForumDto dto);
    Task<List<ForumDto>> GetSubscribedForumsAsync();
    Task<ForumDto> GetForumByNameAsync(string name);
    
    Task<List<ForumDto>> GetAllForumsAsync();
}