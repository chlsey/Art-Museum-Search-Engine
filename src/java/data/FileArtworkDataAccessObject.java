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

    private final File jsonFile;
    private final Map<String, ArrayList> artworks = new HashMap<>();


    public FileArtworkDataAccessObject(String jsonPath) throws IOException {
        jsonFile = new File(jsonPath);
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
                    int rating = (int) jsonObject.get("rating");

                    artwork.add(favorite);
                    artwork.add(rating);
                    artwork.add(comments);
                    artworks.put(jsonArray.toString(), artwork);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

            JsonNode rootNode;
            if (jsonFile.exists() && jsonFile.length() > 0) {
                rootNode = objectMapper.readTree(jsonFile);  // parsing existing file
            } else {
                rootNode = objectMapper.createObjectNode();  // file empty creating a new object node
            }

            // Ensure the "artworks" field exists, create it if it doesn't
            ArrayNode artworksArray;
            if (rootNode.has("artworks")) {
                artworksArray = (ArrayNode) rootNode.get("artworks");
            } else {
                artworksArray = objectMapper.createArrayNode();
                ((ObjectNode) rootNode).set("artworks", artworksArray);
            }

            // Create a new JSON object for the new artwork
            ObjectNode newArtwork = objectMapper.createObjectNode();
            newArtwork.put("id", artwork.getId());
            newArtwork.put("favorite", artwork.checkFavorited());
            newArtwork.put("rating", artwork.getRating());

            ArrayNode commentsArray = objectMapper.createArrayNode();
            for (String comment : artwork.getComments()) {
                commentsArray.add(comment);
            }
            newArtwork.set("comments", commentsArray);

            // Add the new artwork to the "artworks" array
            artworksArray.add(newArtwork);

            // Write the updated structure back to the file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);

        } catch (IOException e) {
            e.printStackTrace();
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
            finalArt.setRating(parseInt(node.get("numRate").toString()));
            return finalArt;
        } else {
            MuseumDataAccessObject DAObj = new MuseumDataAccessObject();
            return DAObj.getArtworkById(id);
        }
    }

    @Override // TODO: must fix this to work properly
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

//    @Override
//    public void updateRating(Artwork artwork) {
//
//    }

    @Override
    public void saveRating(Artwork artwork) throws IOException {

    }

    @Override
    public int getRating() {
        return 0;
    }

    @Override
    public void setRating(int rating) {

    }

    @Override
    public List<Artwork> searchArtwork(String searchMessage) {
        return List.of();
    }
}
