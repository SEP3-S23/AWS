namespace Shared.DTOs;

public class CreateForumDto
{
    private string ForumName;

    private string ForumDescription;

    private string ForumCategory;

    public CreateForumDto(string name, string description, string cagetory)
    {
        ForumName = name;
        ForumDescription = description;
        ForumCategory = cagetory;
    }

}