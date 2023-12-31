package org.example;

import com.github.tomakehurst.wiremock.http.Body;
import org.example.entity.WeatherInformation;
import org.example.entity.WeatherInformation;
import org.json.JSONException;
import org.json.JSONObject;

public class Contents {

    public static Body apiResponseAsBody() {
        return new Body("""
                        {
                                    "location": {
                                        "name": "Lichtervelde",
                                        "region": "",
                                        "country": "Belgium",
                                        "lat": 51.03,
                                        "lon": 3.15,
                                        "tz_id": "Europe/Brussels",
                                        "localtime_epoch": 1698659221,
                                        "localtime": "2023-10-30 10:47"
                                    },
                                    "current": {
                                        "last_updated_epoch": 1698658200,
                                        "last_updated": "2023-10-30 10:30",
                                        "temp_c": 11.0,
                                        "temp_f": 51.8,
                                        "is_day": 1,
                                        "condition": {
                                            "text": "Moderate rain",
                                            "icon": "//cdn.weatherapi.com/weather/64x64/day/302.png",
                                            "code": 1189
                                        },
                                        "wind_mph": 8.1,
                                        "wind_kph": 13.0,
                                        "wind_degree": 140,
                                        "wind_dir": "SE",
                                        "pressure_mb": 999.0,
                                        "pressure_in": 29.5,
                                        "precip_mm": 0.0,
                                        "precip_in": 0.0,
                                        "humidity": 94,
                                        "cloud": 75,
                                        "feelslike_c": 8.7,
                                        "feelslike_f": 47.7,
                                        "vis_km": 10.0,
                                        "vis_miles": 6.0,
                                        "uv": 3.0,
                                        "gust_mph": 16.1,
                                        "gust_kph": 25.8
                                    }
                                }
                """);
    }

    public static WeatherInformation apiResponse() throws JSONException {
        return WeatherInformation.of(new JSONObject("""
                        {
                                    "location": {
                                        "name": "Lichtervelde",
                                        "region": "",
                                        "country": "Belgium",
                                        "lat": 51.03,
                                        "lon": 3.15,
                                        "tz_id": "Europe/Brussels",
                                        "localtime_epoch": 1698659221,
                                        "localtime": "2023-10-30 10:47"
                                    },
                                    "current": {
                                        "last_updated_epoch": 1698658200,
                                        "last_updated": "2023-10-30 10:30",
                                        "temp_c": 11.0,
                                        "temp_f": 51.8,
                                        "is_day": 1,
                                        "condition": {
                                            "text": "Moderate rain",
                                            "icon": "//cdn.weatherapi.com/weather/64x64/day/302.png",
                                            "code": 1189
                                        },
                                        "wind_mph": 8.1,
                                        "wind_kph": 13.0,
                                        "wind_degree": 140,
                                        "wind_dir": "SE",
                                        "pressure_mb": 999.0,
                                        "pressure_in": 29.5,
                                        "precip_mm": 0.0,
                                        "precip_in": 0.0,
                                        "humidity": 94,
                                        "cloud": 75,
                                        "feelslike_c": 8.7,
                                        "feelslike_f": 47.7,
                                        "vis_km": 10.0,
                                        "vis_miles": 6.0,
                                        "uv": 3.0,
                                        "gust_mph": 16.1,
                                        "gust_kph": 25.8
                                    }
                                }
                """));
    }

    public static WeatherInformation apiResponseWithoutCity() throws JSONException {
        return WeatherInformation.of(new JSONObject("""
                        {
                                    "location": {
                                        "region": "",
                                        "country": "Belgium",
                                        "lat": 51.03,
                                        "lon": 3.15,
                                        "tz_id": "Europe/Brussels",
                                        "localtime_epoch": 1698659221,
                                        "localtime": "2023-10-30 10:47"
                                    },
                                    "current": {
                                        "last_updated_epoch": 1698658200,
                                        "last_updated": "2023-10-30 10:30",
                                        "temp_c": 11.0,
                                        "temp_f": 51.8,
                                        "is_day": 1,
                                        "condition": {
                                            "text": "Moderate rain",
                                            "icon": "//cdn.weatherapi.com/weather/64x64/day/302.png",
                                            "code": 1189
                                        },
                                        "wind_mph": 8.1,
                                        "wind_kph": 13.0,
                                        "wind_degree": 140,
                                        "wind_dir": "SE",
                                        "pressure_mb": 999.0,
                                        "pressure_in": 29.5,
                                        "precip_mm": 0.0,
                                        "precip_in": 0.0,
                                        "humidity": 94,
                                        "cloud": 75,
                                        "feelslike_c": 8.7,
                                        "feelslike_f": 47.7,
                                        "vis_km": 10.0,
                                        "vis_miles": 6.0,
                                        "uv": 3.0,
                                        "gust_mph": 16.1,
                                        "gust_kph": 25.8
                                    }
                                }
                """));
    }

    public static WeatherInformation apiResponseWithoutTemp() throws JSONException {
        return WeatherInformation.of(new JSONObject("""
                        {
                                    "location": {
                                        "name": "Lichtervelde",
                                        "region": "",
                                        "country": "Belgium",
                                        "lat": 51.03,
                                        "lon": 3.15,
                                        "tz_id": "Europe/Brussels",
                                        "localtime_epoch": 1698659221,
                                        "localtime": "2023-10-30 10:47"
                                    },
                                    "current": {
                                        "last_updated_epoch": 1698658200,
                                        "last_updated": "2023-10-30 10:30",
                                        "is_day": 1,
                                        "condition": {
                                            "text": "Moderate rain",
                                            "icon": "//cdn.weatherapi.com/weather/64x64/day/302.png",
                                            "code": 1189
                                        },
                                        "wind_mph": 8.1,
                                        "wind_kph": 13.0,
                                        "wind_degree": 140,
                                        "wind_dir": "SE",
                                        "pressure_mb": 999.0,
                                        "pressure_in": 29.5,
                                        "precip_mm": 0.0,
                                        "precip_in": 0.0,
                                        "humidity": 94,
                                        "cloud": 75,
                                        "feelslike_c": 8.7,
                                        "feelslike_f": 47.7,
                                        "vis_km": 10.0,
                                        "vis_miles": 6.0,
                                        "uv": 3.0,
                                        "gust_mph": 16.1,
                                        "gust_kph": 25.8
                                    }
                                }
                """));
    }

    public static WeatherInformation apiResponseWithoutConditionText() throws JSONException {
        return WeatherInformation.of(new JSONObject("""
                        {
                                    "location": {
                                        "name": "Lichtervelde",
                                        "region": "",
                                        "country": "Belgium",
                                        "lat": 51.03,
                                        "lon": 3.15,
                                        "tz_id": "Europe/Brussels",
                                        "localtime_epoch": 1698659221,
                                        "localtime": "2023-10-30 10:47"
                                    },
                                    "current": {
                                        "last_updated_epoch": 1698658200,
                                        "last_updated": "2023-10-30 10:30",
                                        "temp_c": 11.0,
                                        "temp_f": 51.8,
                                        "is_day": 1,
                                        "condition": {
                                            "icon": "//cdn.weatherapi.com/weather/64x64/day/302.png",
                                            "code": 1189
                                        },
                                        "wind_mph": 8.1,
                                        "wind_kph": 13.0,
                                        "wind_degree": 140,
                                        "wind_dir": "SE",
                                        "pressure_mb": 999.0,
                                        "pressure_in": 29.5,
                                        "precip_mm": 0.0,
                                        "precip_in": 0.0,
                                        "humidity": 94,
                                        "cloud": 75,
                                        "feelslike_c": 8.7,
                                        "feelslike_f": 47.7,
                                        "vis_km": 10.0,
                                        "vis_miles": 6.0,
                                        "uv": 3.0,
                                        "gust_mph": 16.1,
                                        "gust_kph": 25.8
                                    }
                                }
                """));
    }


    public static WeatherRegistered expectedWeatherRegistered() {
        return WeatherRegistered.newBuilder()
                .setLocation("Lichtervelde")
                .setTempC(11.0)
                .setCondition("Moderate rain")
                .build();
    }

}
