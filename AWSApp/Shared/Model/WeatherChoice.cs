﻿namespace Shared.Model;

public static class WeatherChoice
{
public static string Temperature = "temperature";
public static string Humidity = "humidity";
public static string UVIndex = "UV index";
public static string WindSpeed = "wind speed";
public static string WindDirection = "wind direction";
public static string Pressure = "pressure";
public static string RainQuantity = "rain quantity";

public static string[] Choices =
{
    "temperature",
    "humidity",
    "UV index",
    "wind speed",
    "wind direction",
    "pressure",
    "rain quantity",
};

}