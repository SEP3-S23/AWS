using System.Net;
using System.Net.Http.Json;
using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication;

public class HistoricalDataService : IHistoricalDataService
{
    private readonly HttpClient _httpClient;

    public HistoricalDataService()
    {
        _httpClient = new HttpClient();
    }

    public async Task<HystoricalWeatherDataDto> GetData(string wsName, string sensorName, long startDate, long endDate)
    {
        var dateRangeDto = new DateRangeDto(startDate, endDate);
        var response = await _httpClient.PostAsJsonAsync("http://localhost:8101/historical/" + wsName + "/" + sensorName,dateRangeDto );
        
        if (response.IsSuccessStatusCode)
        {
            Console.WriteLine(response.Content.ToString());
            var data = await response.Content.ReadFromJsonAsync<HystoricalWeatherDataDto>();
            return data;
        }
        throw new Exception($"Request failed with status code {response.StatusCode}");
    }

    public async Task<String[]> GetWSList()
    {
        var response = await _httpClient.GetAsync("http://localhost:8101/api/ws/list");

        if (response.IsSuccessStatusCode)
        {
            var data = await response.Content.ReadFromJsonAsync<String[]>();
            return data;
        }
        throw new Exception($"Request failed with status code {response.StatusCode}");
    }
}