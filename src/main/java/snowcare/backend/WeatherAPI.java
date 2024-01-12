package snowcare.backend;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import snowcare.backend.domain.User;
import snowcare.backend.repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherAPI {
    private final UserRepository userRepository;

    @Value("${api.key}")
    private String apiKey;

    // 매일 아침 9시에 실행
    @Scheduled(cron = "0 0 9 * * ?")
    public void callWeather() throws IOException {
        List<User> users = userRepository.findByWeatherAlarmIsTrue();

        for (User user : users) {
            try {
                // OpenWeatherMap API connection
                URL weatherUrl = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+user.getLatitude()+"&lon="+user.getLongitude()+"&appid="+ apiKey);
                HttpURLConnection weatherConn = (HttpURLConnection) weatherUrl.openConnection();
                weatherConn.setRequestMethod("GET");
                weatherConn.setRequestProperty("Content-type", "application/json");
                weatherConn.setDoOutput(true);

                // 카카오톡 메세지 api connection
                URL kakaoUrl = new URL("https://kapi.kakao.com/v2/api/talk/memo/default/send");
                HttpURLConnection kakaoConn = (HttpURLConnection) kakaoUrl.openConnection();
                kakaoConn.setRequestMethod("POST");
                kakaoConn.setRequestProperty("Authorization", "Bearer "+ access_token);
                kakaoConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                kakaoConn.setDoOutput(true);

                // 날씨 정보 가져오기
                try {
                    StringBuffer sb = new StringBuffer();
                    BufferedReader br = new BufferedReader(new InputStreamReader(weatherConn.getInputStream(), "UTF-8"));
                    while(br.ready()) {
                        sb.append(br.readLine());
                    }

                    // 가져온 날씨 정보 파싱
                    JSONParser parser = new JSONParser();
                    JSONObject data = (JSONObject)parser.parse(sb.toString());
                    JSONArray weather = (JSONArray) data.get("weather");
                    JSONObject weather_info = (JSONObject) weather.get(0);

                    // weather main 종류 : Thunderstorm, Drizzle, Rain, Snow, Atmosphere, Clear, Clouds
                    String todaysWeather = (String) weather_info.get("main");
                    String weatherDescription = (String) weather_info.get("description");

                    // 눈이 올 때만 알림 주기
                    if (todaysWeather.equals("Snow")) {
                        System.out.println("weather : " + todaysWeather);
                        System.out.println("description : " + weatherDescription);
                        System.out.println("눈치우기 봉사활동을 할 수 있는 날이에요");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
