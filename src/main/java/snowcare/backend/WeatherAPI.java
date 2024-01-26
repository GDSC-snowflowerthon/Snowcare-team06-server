package snowcare.backend;

import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import snowcare.backend.domain.User;
import snowcare.backend.repository.UserRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherAPI {
    private final UserRepository userRepository;

    @Value("${api.key}")
    private String apiKey;

    @Value("${access_token")
    private String accessToken;

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
                kakaoConn.setRequestProperty("Authorization", "Bearer "+ accessToken);
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

                    // weather main 종류 : Thunderstorm, Drizzle, Rain, Snow, Atmosphere, Clear, Clouds
                    JSONArray weather = (JSONArray) data.get("weather");
                    JSONObject weatherInfo = (JSONObject) weather.get(0);
                    String todaysWeather = (String) weatherInfo.get("main");
                    String weatherDescription = (String) weatherInfo.get("description");

//                    JSONArray temperature = (JSONArray) data.get("main");
//                    JSONObject tempInfo = (JSONObject) temperature.get(0);
//                    Double tempNow = ((Double) tempInfo.get("temp")) - 273.15;
//                    Double tempMax = ((Double) tempInfo.get("temp_max")) - 273.15;
//                    Double tempMin = ((Double) tempInfo.get("temp_min")) - 273.15;
//                    int humidity = (int) tempInfo.get("humidity");

                    // 눈이 올 때만 알림 주기
                    if (todaysWeather.equals("Snow")) {

                        // 카카오톡 메세지 전송 데이터 설정
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(kakaoConn.getOutputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        JsonObject kakaoJson = new JsonObject();
                        kakaoJson.addProperty("object_type", "text");
                        kakaoJson.addProperty("text", "현재 기온 : -13\n최대 기온 : -9\n최저 기온 : -17\n습도 : 67\n눈이 오는 날이에요! 눈치우기 봉사활동을 하러 가볼까요?");

                        JsonObject link = new JsonObject();
                        link.addProperty("web_url", "http://localhost:3000");

                        kakaoJson.add("link", link.getAsJsonObject());

                        stringBuilder.append("template_object="+kakaoJson);
                        System.out.println(stringBuilder.toString());

                        bw.write(stringBuilder.toString());
                        bw.flush();

                        bw.close();
                        br.close();
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
