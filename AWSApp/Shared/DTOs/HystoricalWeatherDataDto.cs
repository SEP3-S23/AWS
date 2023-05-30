namespace Shared.DTOs;

public class HystoricalWeatherDataDto
{
        public string Name { get; set; }
        public string WsName { get; set; }
        public List<DataPointDto> Data { get; set; }
        public string unit { get; set; }
 
        
        public string[] GetDates()
        {
                List<string> dates = new List<string>();
        
                foreach (DataPointDto dataPoint in Data)
                {
                        DateTime dateTime = DateTimeOffset.FromUnixTimeSeconds(dataPoint.Date).DateTime;
                        dates.Add(dateTime.ToString("dd-MM-yyyy HH.mm.ss"));
                }
                return dates.ToArray();
        }
        
        public List<double> GetValues()
        {
                List<double> values = new List<double>();
        
                foreach (DataPointDto dataPoint in Data)
                {
                        values.Add(dataPoint.Value);
                }
                return values;
        }
        
}