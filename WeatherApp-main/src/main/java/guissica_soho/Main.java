package guissica_soho;

//standard import stuff
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//for the api call and network request
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//for gson
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("secondary"), 640, 480);
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    

    public static void main(String[] args) {
        launch();

        //pls pls pls pls pls DON'T LEAK THIS API KEY TO ANYONE. I still haven't found a way to hide it.
        String apiKey = "Mcju1L4bcfsPLhHjyL0QgWuFRxuDLI2H";
        String location = "makati";

        // get weather data
        String realtimeWeatherURL = "https://api.tomorrow.io/v4/weather/realtime?location=" + location + "&apikey=" + apiKey;

        // get forecast data
        String forecastWeatherURL = "https://api.tomorrow.io/v4/weather/forecast?location=" + location + "&apikey=" + apiKey;


        try{
            URL url = new URL(realtimeWeatherURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();

                JsonObject dataObject = jsonObject.getAsJsonObject("data");
                JsonObject valuesObject = dataObject.getAsJsonObject("values");
                double temperature = valuesObject.get("temperature").getAsDouble();
                
                System.out.println("Temperature in " + location + ": " + temperature + "°C");
            }else{
                System.out.println("Error: " + responseCode);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}