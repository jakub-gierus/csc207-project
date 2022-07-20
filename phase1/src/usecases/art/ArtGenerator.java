package usecases.art;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

public class ArtGenerator {

    Map<String, String> asciiCategories = new HashMap<>();

    public ArtGenerator() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./storage/asciiCategories.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] asciiCategory = line.split(",");
            this.asciiCategories.put(asciiCategory[0], asciiCategory[1]);
        }
    }

    public String determineClosestCategory(String inputString) {
        int closestDistance = 10000000;
        String closestCategory = null;
        int i = 0;
        for (String category : this.asciiCategories.keySet()) {
            i++;
            int distance = StringUtils.getLevenshteinDistance(category.toUpperCase(), inputString.toUpperCase());
            if (distance < closestDistance && i != 2) {
                closestDistance = distance;
                closestCategory = category;
            }
        }
        return closestCategory;
    }

    public String getRandomArt(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements asciiArts = document.select("pre");
        Element asciiArt = asciiArts.get(new Random().nextInt(asciiArts.size()));
        String safe = Jsoup.clean(asciiArt.text(), Safelist.basic());
        String art = safe + "\nNote: Retrieved from asciiart.eu.\nAny generated art does not belong to this app.";
        return art;
    }
    public String generateArt(String prompt) throws IOException {
        String closestCategory = this.determineClosestCategory(prompt);
        String url = "https://www.asciiart.eu/" + this.asciiCategories.get(closestCategory);
        return getRandomArt(url);
    }
}
