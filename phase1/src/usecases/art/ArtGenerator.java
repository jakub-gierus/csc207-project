package usecases.art;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ArtGenerator {

    public void connectToGoogleImages() throws IOException {
        URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Godfather");
        URLConnection connection = url.openConnection();
        String line;

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while (((line = reader.readLine()) != null)) {
            builder.append(line);
        }
        JSONObject json = new JSONObject(builder.toString());
        String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");

        BufferedImage image = ImageIO.read(new URL(imageUrl));
        File outputFile = new File("image.jpg");
        ImageIO.write(image, "jpg", outputFile);
    }
}
