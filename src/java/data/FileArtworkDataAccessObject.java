package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.Artwork;
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
    private final Map<String, Artwork> artworks = new HashMap<>();


    public FileArtworkDataAccessObject(String jsonPath) throws IOException {
        jsonFile = new File(jsonPath);
        if (jsonFile.length() != 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode rootNode = objectMapper.readTree(jsonFile);
                ArrayNode artworksArray = (ArrayNode) rootNode.get("artworks");
                // Iterate through the artworks and create Artwork objects
                for (JsonNode jsonObject : artworksArray) {

                    // Set the artwork properties using the data from JSON
                    String id = jsonObject.get("id").asText();
                    //System.out.println(id);
                    Artwork artwork = getArtworkById(id);

                    // Set the favorite flag and rating
                    artwork.setFavorited(jsonObject.get("favorite").asBoolean());
                    artwork.setRating(jsonObject.get("rating").asInt());

                    // Set comments if available
                    ArrayNode commentsNode = (ArrayNode) jsonObject.get("comments");
                    List<String> comments = new ArrayList<>();
                    for (JsonNode commentNode : commentsNode) {
                        comments.add(commentNode.asText());
                    }
                    artwork.setComments(comments);
                    artworks.put(id, artwork);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateFavorite(String id) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            if (contains(id)) {
                JsonNode node = null;
                for (JsonNode art : rootNode.get("artworks")) {
                    if (art.get("id").asText().equals(id)) {
                        node = art;
                        break;
                    }
                }
                boolean fav = node.get("favorite").asBoolean();
                ((ObjectNode) node).put("favorite", !fav);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
            } else {
                Artwork artwork = getArtworkById(id);
                artwork.setFavorited(!artwork.checkFavorited());
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
            newArtwork.put("id", "MET-"+artwork.getId().replace("MET-",""));
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

            if (artwork != null){
                if (contains(artwork.getId())) {
                    JsonNode node = rootNode.get(artwork.getId());
                    ((ArrayNode) node.get("comments")).add(comment);
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
                } else {
                    artwork.addComment(comment);
                    save(artwork);
                }
            }

        } catch (IOException e) {}
    }

    @Override
    public Artwork getArtworkById(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        if (contains(id) && rootNode.has("artworks")) {
            JsonNode node = null;
            for (JsonNode artwork : rootNode.get("artworks")) {
                if (artwork.get("id").asText().equals(id)) {
                    node = artwork;
                    break;
                }
            }
            MuseumDataAccessObject DAObj = new MuseumDataAccessObject();
            Artwork artwork = DAObj.getArtworkById(id);
            Artwork finalArt = new Artwork(artwork.getTitle(), artwork.getArtistName(), artwork.getCompositionDate(),
                    artwork.getGallery(), artwork.getImageUrl(), artwork.getKeyWords(), artwork.getDescription(), id);
            for (JsonNode comment : node.get("comments")) {
                finalArt.addComment(comment.toString());
            }
            if (node.get("favorite").toString().equals("True")) {
                finalArt.setFavorited(!artwork.checkFavorited());
            }
            finalArt.setRating(parseInt(node.get("rating").toString()));
            return finalArt;
        } else {
            MuseumDataAccessObject DAObj = new MuseumDataAccessObject();
            return DAObj.getArtworkById(id);
        }
    }

    /**
     * Precondition: jsonFile is not empty.
     * @param id is the unique id of the artwork
     * @return whether artwork with id is in the database.
     * @throws IOException
     */
    @Override
    public boolean contains(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        ArrayNode artworks = (ArrayNode) rootNode.get("artworks");
        for (JsonNode artwork: artworks) {
            if (artwork.get("id").asText().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Artwork> getAllFavorites() {
        List<Artwork> favoriteArtworks = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            ArrayNode artworksArray = (ArrayNode) rootNode.get("artworks");
            for (JsonNode node : artworksArray) {
                if (node.get("favorite").asBoolean()) {
                    favoriteArtworks.add(convertJsonToArtwork(node));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return favoriteArtworks;
    }

    private Artwork convertJsonToArtwork(JsonNode node) {
        try {
            String id = node.get("id").asText();

            Artwork artwork = getArtworkById(id);

            return artwork;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    public List<Artwork> getCommentedArtworks(){
        List<Artwork> commentedArtworks = new ArrayList<>();
        for (Artwork artwork : artworks.values()) {
            if (!artwork.getComments().isEmpty()) {
                commentedArtworks.add(artwork);
            }
        }
        return commentedArtworks;
    }

    public int getRating(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        if (contains(id)) {
            JsonNode node = rootNode.get(id);
            if (node.has("rating") && node.get("rating").isInt()) {
                return node.get("rating").asInt();
            }
        }
        return 0;
    }

    @Override
    public List<Artwork> getRatedArtworks() {
        List<Artwork> ratedArtworks = new ArrayList<>();
        for (Artwork artwork : artworks.values()) {
            if (artwork.getRating() > 0) {
                ratedArtworks.add(artwork);
            }
        }
        return ratedArtworks;
    }

    @Override
    public List<Artwork> searchArtwork(String searchMessage) {
        return List.of();
    }
}
