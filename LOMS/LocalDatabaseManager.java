package com.example.loms;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONException;

public class LocalDatabaseManager extends AppCompatActivity{
    private static final String FILE_PATH = "local_data.json";

    // 로컬 데이터 읽기
    public JSONArray readLocalData() throws JSONException{
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            return new JSONArray(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray(); // 파일이 없거나 오류 시 빈 배열 반환
        }
    }

    // 로컬 데이터 저장
    public void writeLocalData(JSONArray data) throws JSONException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(data.toString(4)); // JSON 저장
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

