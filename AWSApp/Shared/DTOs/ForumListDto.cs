using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace Shared.DTOs;


public class ForumListDto
{
    [JsonProperty("timeCreation")]
    public DateTime TimeCreation { get; set; }

    [JsonProperty("name")]
    public string Name { get; set; }

    [JsonProperty("description")]
    public string Description { get; set; }

    [JsonProperty("category")]
    public string Category { get; set; }

    public ForumListDto(DateTime timeCreation, string name, string description, string category)
    {
        TimeCreation = timeCreation;
        Name = name;
        Description = description;
        Category = category;
    }

    public ForumListDto(){}
}