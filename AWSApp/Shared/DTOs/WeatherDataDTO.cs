using System.Globalization;

namespace Shared.Model;

public class WeatherDataDTO
{
    public string id { get; set; }
    public string date_time { get; set; }
    public string name { get; set; }
    public string value { get; set; }
    public string unit { get; set; }
    public string wsName { get; set; }
    private string format = "dd-MM-yyyy HH.mm.ss";

    public DateTime getDateFormat()
    {
        return DateTime.ParseExact(date_time, format, CultureInfo.InvariantCulture); 
    }
    
    
}