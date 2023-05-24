using Shared.Model;

namespace Backend.Services.Forums;

public interface IForumService
{
    Task<Forum> CreateForum(string title, string description, string category);
    Task<string> GetPageAsync();
}