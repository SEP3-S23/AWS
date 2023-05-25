using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using Client;
using MudBlazor.Services;
using Backend.Services;
using BackEnd.Services.Authentication;
using BackEnd.Services.Authentication.Post;

var builder = WebAssemblyHostBuilder.CreateDefault(args);
builder.RootComponents.Add<App>("#app");
builder.RootComponents.Add<HeadOutlet>("head::after");

builder.Services.AddScoped(sp => new HttpClient { BaseAddress = new Uri(builder.HostEnvironment.BaseAddress) });
builder.Services.AddMudServices();

// Register the ForumService
builder.Services.AddScoped<ForumService>();

builder.Services.AddScoped<IPostService, PostHttpClient>();

await builder.Build().RunAsync();