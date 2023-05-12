using System.Text;
using System.Text.Json;

namespace BackEnd.UserMethodCalls;

public class UserService : IUserService
{
    public async Task<string> Login(string username, string password)
    {
        using (HttpClient httpClient = new HttpClient())
        {
            string apiUrl = "http://localhost:8090/api/user/login";

            // create the data to send in the request body
            var requestData = new {
                username = username,
                password = password
            };
            
            var requestDataJson = JsonSerializer.Serialize(requestData);
            var requestDataContent = new StringContent(requestDataJson, Encoding.UTF8, "application/json");

            // make the post request with the data in the request body
            HttpResponseMessage response = await httpClient.PostAsync(apiUrl, requestDataContent);

            if (response.IsSuccessStatusCode)
            {
                string responseBody = await response.Content.ReadAsStringAsync();
                return responseBody;
            }
            else
            {
                // Handle error
                return null;
            }
        }
    }
}