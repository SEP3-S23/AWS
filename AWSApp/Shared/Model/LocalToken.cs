namespace Shared.Model;

public class LocalToken
{
    private static LocalToken instance = null;
    private static readonly object padlock = new object();
    public string token { get; set; } = "";

    LocalToken()
    {
        
    }
    
    public static LocalToken Instance
    {
        get
        {
            if (instance == null)
            {
                lock (padlock)
                {
                    if (instance == null)
                    {
                        instance = new LocalToken();
                    }
                }
            }
            return instance;
        }
    }
    
}








public sealed class Singleton
{
    private static Singleton instance = null;
    private static readonly object padlock = new object();

    Singleton()
    {
    }

    public static Singleton Instance
    {
        get
        {
            if (instance == null)
            {
                lock (padlock)
                {
                    if (instance == null)
                    {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }
}