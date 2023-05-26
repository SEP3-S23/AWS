namespace Shared.Token;

public class AuthenticationResponse
{
 
    
    public string token { get; set; }
    
    public AuthenticationResponse(string token)
    {
        this.token = token;
    }
    
}