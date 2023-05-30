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

    

    public double GetValueByName(string valueName)
    {
        if (valueName == name)
        {
            return Math.Round(Convert.ToDouble(this.value), 1);
        }

        return 0.0;
    }
    
    
}