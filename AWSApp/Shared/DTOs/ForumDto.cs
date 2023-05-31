using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace Shared.DTOs;


public class ForumDto
{
    [JsonProperty("timeCreation")]
    public DateTime TimeCreation { get; set; }

    [JsonProperty("name")]
    public string Name { get; set; }

    [JsonProperty("description")]
    public string Description { get; set; }

    [JsonProperty("category")]
    public string Category { get; set; }

    public ForumDto(DateTime timeCreation, string name, string description, string category)
    {
        this.TimeCreation = timeCreation;
        this.Name = name;
        this.Description = description;
        this.Category = category;
    }

    public ForumDto(){}
}