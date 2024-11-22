package com.example.loms;
import androidx.appcompat.app.AppCompatActivity;
import java.net.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
public class RemoteDatabaseManager extends AppCompatActivity{
    private static final String SERVER_URL = "https://your-server-url.com/api/data";

    // 서버로부터 데이터 가져오기
    public JSONArray fetchServerData() throws IOException, JSONException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return new JSONArray(response.toString());
        }
    }

    // 서버에 데이터 업데이트하기
    public void updateServerData(JSONArray data) throws IOException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
            writer.write(data.toString());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("서버 동기화 성공!");
        } else {
            System.err.println("서버 동기화 실패: " + connection.getResponseCode());
        }
    }

    private static JSONArray mergeData(JSONArray localData, JSONArray serverData) throws JSONException{
        // 병합 로직 구현 (중복 확인 및 데이터 갱신)
        for (int i = 0; i < serverData.length(); i++) {
            JSONObject serverItem = serverData.getJSONObject(i);
            boolean exists = false;
            for (int j = 0; j < localData.length(); j++) {
                if (localData.getJSONObject(j).getString("id").equals(serverItem.getString("id"))) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                localData.put(serverItem);
            }
        }
        return localData;
    }
}
