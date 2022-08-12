package usecases.art;

import entity.art.Art;

import java.util.*;

/**
 * Appraises the value of an art piece
 **/
public class Appraiser {
    /**
     * Returns the value of an art piece based on total issues of the art
     * @param library => library of arts pieces in system
     * @param art => the art piece to appraise
     */
    public void appraiseArt(ArtManager library, Art art){

        Collection<Art> arts = library.getAllArt();
        HashMap<Character, Integer> collectionChars = new HashMap<>();

        // Gets most common char in all the arts
        char commonCharAllArts = getMostCommonCharCollection(arts, collectionChars);

        // gets most common char in this art
        HashMap<Character, Integer> artChars = new HashMap<>();
        char commonCharThisArt = getMostCommonChar(art, artChars);

        if(artChars.get(commonCharThisArt) > 0.9*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*0.3));
        } else if (artChars.get(commonCharThisArt) > 0.8*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*0.4));
        } else if (artChars.get(commonCharThisArt) > 0.7*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*0.5));
        } else if (artChars.get(commonCharThisArt) > 0.6*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*0.6));
        } else if (artChars.get(commonCharThisArt) > 0.5*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*0.7));
        } else if (artChars.get(commonCharThisArt) > 0.4*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*0.8));
        } else if (artChars.get(commonCharThisArt) > 0.3*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*0.9));
        } else if (artChars.get(commonCharThisArt) > 0.2*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*1.0));
        } else if (artChars.get(commonCharThisArt) > 0.1*collectionChars.get(commonCharThisArt)) {
            art.setPrice((float) (art.getPrice()*1.1));
        } else {
            art.setPrice((float) (art.getPrice()*1.2));
        }
    }

    private char getMostCommonCharCollection(Collection<Art> arts, HashMap<Character, Integer> chars) {
        for(Art art: arts) {
            buildCharMap(art, chars);
        }
        chars.remove('\n');
        chars.remove(' ');
        return helper(chars);
    }
    private char getMostCommonChar(Art art, HashMap<Character, Integer> chars) {
        buildCharMap(art, chars);
        chars.remove('\n');
        chars.remove(' ');
        return helper(chars);
    }

    private char helper(HashMap<Character, Integer> chars) {
        int max = Collections.max(chars.values());

        List<Character> keys = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : chars.entrySet()) {
            if (entry.getValue()==max) {
                keys.add(entry.getKey());
            }
        }
        return keys.get(0);
    }

    private void buildCharMap(Art art, HashMap<Character, Integer> chars) {
        char[] charArray = art.getArt().toCharArray();
        for (Character c: charArray) {
            if(chars.containsKey(c)) {
                chars.put(c, chars.get(c) + 1);
            }
            else {
                chars.put(c,1);
            }
        }
    }
}
