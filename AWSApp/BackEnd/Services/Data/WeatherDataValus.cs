namespace BackEnd.Services.Data;

public class WeatherDataValus
{
    public WeatherDataValus(string temperature,  string humidity,string heat, string windSpeed, string windDirection,string  pressure,string  day, string rainQuantity)
    {
        this.Temperature = Convert.ToDouble(temperature);
        this.Humidity = Convert.ToDouble(humidity);
        this.Heat = Convert.ToDouble(heat);
        this.WindSpeed = Convert.ToDouble(windSpeed);
        this.WindDirection = Convert.ToDouble(windDirection);
        this.Pressure = Convert.ToDouble(pressure);
        this.RainQuantity = Convert.ToDouble(rainQuantity);
        this.Day = day;
    }
    
    public WeatherDataValus(){}
    
    
    public double? Temperature  { get; set; }
    public double? Humidity  { get; set; }
    public double? Heat { get; set; }
    public double? WindSpeed  { get; set; }
    public double? WindDirection { get; set; }
    public double? Pressure { get; set; }
    public double? RainQuantity { get; set; }
    public string? Day { get; set; }
}



