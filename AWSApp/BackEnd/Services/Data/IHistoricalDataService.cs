using Shared.DTOs;
using Shared.Model;

namespace BackEnd.Services.Authentication;

public interface IHistoricalDataService
{
    Task<HystoricalWeatherDataDto> GetData(string wsName, string sensorName, long startDate, long endDate);
  
}