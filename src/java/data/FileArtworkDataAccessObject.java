package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.Artwork;
import org.json.JSONObject;
import use_case.click_art.ClickArtDataAccessInterface;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.comment.CommentDataAccessInterface;
import use_case.rating.RatingDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class FileArtworkDataAccessObject implements CommentDataAccessInterface, FavoriteDataAccessInterface, SearchDataAccessInterface, RatingDataAccessInterface, ClickArtDataAccessInterface {

//    private static final String HEADER = "title,artistname,timeperiod,gallery,imageURL,comment,keywords";

    private final File jsonFile;
//    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, ArrayList> artworks = new HashMap<>();
//    private final Map<String, List<String>> commentsMap = new HashMap<>();


    public FileArtworkDataAccessObject(String jsonPath) throws IOException {
        jsonFile = new File(jsonPath);
//        headers.put("id", 0);
//        headers.put("favorites", 1);
//        headers.put("totalScore", 2);
//        headers.put("numRate", 3);
//        headers.put("comments", 4);


        if (jsonFile.length() != 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Parse the JSON file into a List of Maps
                List<Map<String, Object>> jsonArray = objectMapper.readValue(
                        jsonFile,
                        new TypeReference<List<Map<String, Object>>>() {}
                );

                // Iterate through the list and add
                for (int i = 0; i < jsonArray.size(); i++) {
                    Map<String, Object> jsonObject = jsonArray.get(i);
                    ArrayList artwork = new ArrayList();
                    ArrayList<String> comments = objectMapper.readValue(jsonObject.get("comments").toString(), new TypeReference<ArrayList<String>>() {});
                    Boolean favorite = Boolean.parseBoolean((String) jsonObject.get("favorite"));
                    int numRate = (int) jsonObject.get("numRate");
                    int totalScore = (int) jsonObject.get("totalScore");

                    artwork.add(favorite);
                    artwork.add(numRate);
                    artwork.add(totalScore);
                    artwork.add(comments);
                    artworks.put(jsonArray.toString(), artwork);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        else {
//            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
//                final String header = reader.readLine();
//
//                if (!header.equals(HEADER)) {
//                    throw new RuntimeException(String.format("header should be%n: %s%but was:%n%s", HEADER, header));
//                }
//
//                String row;
//                while ((row = reader.readLine()) != null) {
//                    final String[] col = row.split(",");
//                    final String title = String.valueOf(col[headers.get("title")]);
//                    final String artistname = String.valueOf(col[headers.get("artistname")]);
//                    final String timeperiod = String.valueOf(col[headers.get("timeperiod")]);
//                    final String gallery = String.valueOf(col[headers.get("gallery")]);
//                    final String image = String.valueOf(col[headers.get("imageURL")]);
//                    final String keywords = String.valueOf(col[headers.get("keywords")]);
//                    final String commentString = String.valueOf(col[headers.get("comments")]);
//                    // TODO: must implement multiple keywords function
//                    final Artwork artwork = new Artwork(title, artistname, timeperiod,
//                            gallery, image, keywords, "description not found"); // TODO: could implement artwork factory instead.
//                    artworks.put(title, artwork); // TODO: could put something else in the place of title to make it easier to search things up.
//
//                    if (!commentString.isEmpty()) {
//                        List<String> commentList = List.of(commentString.split(";")); // Assuming comments are ';'-separated
//                        commentsMap.put(title, new ArrayList<>(commentList));
//                    }
//                }
//            }
//        }
    }

//    private void save() throws IOException {
//        final BufferedWriter writer;
//        try {


//            writer = new BufferedWriter(new FileWriter(jsonFile));
//            writer.write(String.join(",", headers.keySet()));
//            writer.newLine();
//
//            for (Artwork artwork : artworks.values()) {
//                List<String> commentList = commentsMap.getOrDefault(artwork.getTitle(), new ArrayList<>());
//                String comments = String.join(";", commentList);
//                String line = String.format("%s,%s,%s,%s,%s,%s",
//                        artwork.getTitle(),
//                        artwork.getArtistName(),
//                        artwork.getCompositionDate(),
//                        artwork.getGallery(),
//                        artwork.getImageUrl());
//                // TODO: implement list of keywords for each artwork (String key : artwork.getKeyWords())
//                writer.write(line);
//                writer.newLine();
//            }
//
//            writer.close();
//        }
//        catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

    @Override
    public void updateFavorite(Artwork artwork) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            if (contains(artwork.getId())) {
                JsonNode node = rootNode.get(artwork.getId());
                ((ObjectNode) node).put("favorite", !artwork.checkFavorited());
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
            } else {
                artwork.setFavorited();
                save(artwork);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Saves a new artwork to the JSONFile.
     */
    @Override
    public void save(Artwork artwork) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            ObjectNode newObject = objectMapper.createObjectNode();
            newObject.put("favorite", artwork.checkFavorited());
            newObject.put("numRate", artwork.getnumRate());
            newObject.put("totalScore", artwork.getTotalScore());
            ArrayNode commentsArray = objectMapper.createArrayNode();

            for (String comment: artwork.getComments()) {
                commentsArray.add(comment);
            }
            newObject.set("comments", commentsArray);
            ((ArrayNode) rootNode).add(newObject);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
        } catch (IOException e) {
        }
    }


    @Override
    public void addCommentToArtwork(Artwork artwork, String comment) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            if (contains(artwork.getId())) {
                JsonNode node = rootNode.get(artwork.getId());
                ((ArrayNode) node.get("comments")).add(comment);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
            } else {
                artwork.addComment(comment);
                save(artwork);
            }
        } catch (IOException e) {}

//
//        if (!artworks.containsKey(artworkTitle)) {
//            throw new IllegalArgumentException("Artwork not found: " + artworkTitle);
//        }
//        commentsMap.computeIfAbsent(artworkTitle, k -> new ArrayList<>()).add(comment);
//        save();
    }

    @Override
    public Artwork getArtworkById(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        if (contains(id)) {
            JsonNode node = rootNode.get(id);
            MuseumDataAccessObject DAObj = new MuseumDataAccessObject();
            Artwork artwork = DAObj.getArtworkById(id);
            Artwork finalArt = new Artwork(artwork.getTitle(), artwork.getArtistName(), artwork.getCompositionDate(),
                    artwork.getGallery(), artwork.getImageUrl(), artwork.getKeyWords(), artwork.getDescription(), id);
            for (JsonNode comment : node.get("comments")) {
                finalArt.addComment(comment.toString());
            }
            if (node.get("favorite").toString().equals("True")) {
                finalArt.setFavorited();
            }
            finalArt.setNumRate(parseInt(node.get("numRate").toString()));
            finalArt.setTotalScore(parseInt(node.get("totalScore").toString()));
            return finalArt;
        } else {
            MuseumDataAccessObject DAObj = new MuseumDataAccessObject();
            return DAObj.getArtworkById(id);
        }
    }

    @Override
    public boolean contains(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        boolean containsId = false;
        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                if (node.has(id)) {
                    containsId = true;
                    break;
                }
            }
        }
        return containsId;
    }

    public List<String> getCommentsForArtwork(Artwork artwork) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        if (contains(artwork.getId())) {
            JsonNode node = rootNode.get(artwork.getId());
            ArrayNode comments = (ArrayNode) node.get("comments");
            List<String> commentList = new ArrayList<>();
            for (JsonNode comment: comments) {
                commentList.add(comment.toString());
            }
            return commentList;
        } else {
            return null;
        }
    }

    @Override
    public int getRating() {
        return 0;
    }

    @Override
    public void setRating(int rating) {

    }

    @Override
    public void incrementRatingCount(int ratingValue) {

    }

    @Override
    public double calculateAverageRating() {
        return 0;
    }

    @Override
    public List<Artwork> searchArtwork(String searchMessage) {
        return List.of();
    }
}
