namespace Shared.Model;

public class Humidity
{

    public Humidity(string id, string dateTime, double value, string wsName)
    {
        this.Id = id;
        this.DateTime = dateTime;
        this.Value = value;
        this.WsName = wsName;

    }
    
    private string Id { get; set; }
    private string DateTime { get; set; }
    private double Value { get; set; }
    private string WsName { get; set; }


    public string ToString()
    {
        return $"id: {Id}" +
               $" role: {DateTime}" +
               $" token: {Value}" +
               $" ExpiresIn: {WsName}"
            ;
    }
}