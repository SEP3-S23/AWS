using Shared.DTOs;
using Shared.Model;

namespace Backend.Services.Forums;

public interface IForumService
{
    
    Task CreateAsync(CreateForumDto dto);
    //Task<string> GetPageAsync();
}