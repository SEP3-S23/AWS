namespace Shared.DTOs;

public class CreateForumDto
{
    public string ForumName;

    public string ForumDescription;

    public string ForumCategory;

    public CreateForumDto(string name, string description, string cagetory)
    {
        ForumName = name;
        ForumDescription = description;
        ForumCategory = cagetory;
    }

}