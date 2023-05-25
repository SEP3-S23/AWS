namespace Shared.DTOs;

public class CreateForumDto
{
    public string name;

    public string description;

    public string category;

    public CreateForumDto(string name, string description, string category)
    {
        this.name = name;
        this.description = description;
        this.category = category;
    }

}