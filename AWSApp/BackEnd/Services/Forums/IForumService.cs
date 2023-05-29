using Shared.DTOs;
using Shared.Model;

namespace Backend.Services.Forums;

public interface IForumService
{
    
    Task<string> CreateAsync(CreateForumDto dto, string token);
    Task<List<ForumListDto>> GetAllForumsAsync();
}