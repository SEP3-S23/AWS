namespace Shared.DTOs;

public class CreateForumDto
{
    public string name { get; set; }

    public string description { get; set; }

    public string category { get; set; }

    public CreateForumDto(string name, string description, string category)
    {
        this.name = name;
        this.description = description;
        this.category = category;
    }

}