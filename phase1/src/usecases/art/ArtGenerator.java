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

    /**
     * Generates a piece of art
     * @throws IOException if file name is invalid
     */
    public ArtGenerator() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./storage/asciiCategories.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] asciiCategory = line.split(",");
            this.asciiCategories.put(asciiCategory[0], asciiCategory[1]);
        }
    }

    /**
     * Determines which category the art piece fits based on the levenstein distance
     * @param inputString the ASCII value of the art
     * @return a String representing the category which this piece fits
     */
    public String determineClosestCategory(String inputString) {
        int closestDistance = 10000000;
        String closestCategory = null;
        int i = 0;
        for (String category : this.asciiCategories.keySet()) {
            int distance = StringUtils.getLevenshteinDistance(category.toUpperCase(), inputString.toUpperCase());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestCategory = category;
            }
        }
        return closestCategory;
    }

    /**
     * Grabs a random piece of ASCII art
     * @param url the url where the art is stored
     * @return a String of the ASCII art
     * @throws IOException if the url is invalid
     */
    public String getRandomArt(String url) throws IOException {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.prettyPrint(false);
        Document document = Jsoup.connect(url).get();
        Elements asciiArts = document.select("pre");
        asciiArts.remove(0);
        asciiArts.remove(0);
        Element asciiArt = asciiArts.get(new Random().nextInt(asciiArts.size()));
        String safe = Jsoup.clean(asciiArt.toString(), "", Safelist.none(), outputSettings);
        String art = safe;
        List<String> result = new ArrayList<>();
        for (String row: art.split("\n")) {
            String filteredRow = row.chars().filter(ch -> ch >= 32 && ch < 127).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            result.add(filteredRow);
        }
        result.add("Note: Retrieved from asciiart.eu.");
        result.add("Any generated art does not belong to this app.");
        String generatedArt = StringUtils.join(result, "\n");
        generatedArt = generatedArt.replace(",", ".");
        return generatedArt;
    }

    /**
     * Generates the piece of art
     * @param prompt the prompt to generate the art piece with
     * @return a String of ASCII art
     * @throws IOException if the url is invalid
     */
    public String generateArt(String prompt) throws IOException {
        String closestCategory = this.determineClosestCategory(prompt);
        String url = "https://www.asciiart.eu/" + this.asciiCategories.get(closestCategory);
        return getRandomArt(url);
    }
}
