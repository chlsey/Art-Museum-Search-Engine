package data;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Artwork;
import use_case.click_art.ClickArtDataAccessInterface;
import use_case.comment.CommentDataAccessInterface;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.rating.RatingDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

/**
 * File artwork data access object.
 */
public class FileArtworkDataAccessObject implements CommentDataAccessInterface,
        FavoriteDataAccessInterface, SearchDataAccessInterface, RatingDataAccessInterface,
        ClickArtDataAccessInterface {

    private static final String ARTWORKS = "artworks";
    private static final String COMMENTS = "comments";
    private static final String ID = "id";
    private static final String FAVORITE = "favorite";
    private static final String RATING = "rating";
    private static final String FILE_NOT_FOUND_ATTEMPTING_TO_USE_A_DEFAULT_FILE =
            "File not found, attempting to use a default file...";
    private final File jsonFile;
    private final Map<String, Artwork> artworks = new HashMap<>();

    public FileArtworkDataAccessObject(String jsonPath) throws IOException {
        jsonFile = new File(jsonPath);
        if (jsonFile.length() != 0) {
            final ObjectMapper objectMapper = new ObjectMapper();
            try {
                final JsonNode rootNode = objectMapper.readTree(jsonFile);
                final ArrayNode artworksArray = (ArrayNode) rootNode.get(ARTWORKS);
                // Iterate through the artworks and create Artwork objects
                for (JsonNode jsonObject : artworksArray) {

                    // Set the artwork properties using the data from JSON
                    final String id = jsonObject.get(ID).asText();
                    final Artwork artwork = getArtworkById(id);

                    // Set the favorite flag and rating
                    artwork.setFavorited(jsonObject.get(FAVORITE).asBoolean());
                    artwork.setRating(jsonObject.get(RATING).asInt());

                    // Set comments if available
                    final ArrayNode commentsNode = (ArrayNode) jsonObject.get(COMMENTS);
                    final List<String> comments = new ArrayList<>();
                    for (JsonNode commentNode : commentsNode) {
                        comments.add(commentNode.asText());
                    }
                    artwork.setComments(comments);
                    artworks.put(id, artwork);
                }

            }
            catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    @Override
    public void updateFavorite(String id) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode rootNode = objectMapper.readTree(jsonFile);
            if (contains(id)) {
                JsonNode node = null;
                for (JsonNode art : rootNode.get(ARTWORKS)) {
                    if (art.get(ID).asText().equals(id)) {
                        node = art;
                        break;
                    }
                }
                final boolean fav = node.get(FAVORITE).asBoolean();
                ((ObjectNode) node).put(FAVORITE, !fav);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
            }
            else {
                final Artwork artwork = getArtworkById(id);
                artwork.setFavorited(!artwork.checkFavorited());
                save(artwork);
            }
        }
        catch (IOException event) {
            System.err.println(FILE_NOT_FOUND_ATTEMPTING_TO_USE_A_DEFAULT_FILE);
        }
    }

    /**
     * Saves a new artwork to the JSONFile.
     */
    @Override
    public void save(Artwork artwork) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final JsonNode rootNode;
            if (jsonFile.exists() && jsonFile.length() > 0) {
                rootNode = objectMapper.readTree(jsonFile);
            }
            else {
                rootNode = objectMapper.createObjectNode();
            }

            // Ensure the "artworks" field exists, create it if it doesn't
            final ArrayNode artworksArray;
            if (rootNode.has(ARTWORKS)) {
                artworksArray = (ArrayNode) rootNode.get(ARTWORKS);
            }
            else {
                artworksArray = objectMapper.createArrayNode();
                ((ObjectNode) rootNode).set(ARTWORKS, artworksArray);
            }

            // Create a new JSON object for the new artwork
            final ObjectNode newArtwork = objectMapper.createObjectNode();
            newArtwork.put(ID, "MET-" + artwork.getId().replace("MET-", ""));
            newArtwork.put(FAVORITE, artwork.checkFavorited());
            newArtwork.put(RATING, artwork.getRating());

            final ArrayNode commentsArray = objectMapper.createArrayNode();
            for (String comment : artwork.getComments()) {
                commentsArray.add(comment);
            }
            newArtwork.set(COMMENTS, commentsArray);

            artworksArray.add(newArtwork);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);

        }
        catch (IOException event) {
            event.printStackTrace();
        }
    }

    @Override
    public void addCommentToArtwork(Artwork artwork, String comment) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode rootNode = objectMapper.readTree(jsonFile);

            if (artwork != null) {
                final String id = artwork.getId();
                if (contains(id)) {
                    JsonNode node = null;
                    for (JsonNode art : rootNode.get(ARTWORKS)) {
                        if (art.get("id").asText().equals(id)) {
                            node = art;
                            break;
                        }
                    }
                    ((ArrayNode) node.get(COMMENTS)).add(comment);
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
                }
                else {
                    artwork.addComment(comment);
                    save(artwork);
                }
            }

        }
        catch (IOException event) {
            System.err.println(FILE_NOT_FOUND_ATTEMPTING_TO_USE_A_DEFAULT_FILE);
        }
    }

    @Override
    public Artwork getArtworkById(String id) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode rootNode = objectMapper.readTree(jsonFile);
        if (contains(id) && rootNode.has(ARTWORKS)) {
            JsonNode node = null;
            for (JsonNode artwork : rootNode.get(ARTWORKS)) {
                if (artwork.get(ID).asText().equals(id)) {
                    node = artwork;
                    break;
                }
            }
            final MuseumDataAccessObject baoBj = new MuseumDataAccessObject();
            final Artwork artwork = baoBj.getArtworkById(id);
            final Artwork finalArt = new Artwork(artwork.getTitle(), artwork.getArtistName(),
                    artwork.getCompositionDate(),
                    artwork.getGallery(), artwork.getImageUrl(), artwork.getKeyWords(), artwork.getDescription(), id);
            for (JsonNode comment : node.get(COMMENTS)) {
                finalArt.addComment(comment.toString());
            }
            if (node.get(FAVORITE).toString().equals("True")) {
                finalArt.setFavorited(!artwork.checkFavorited());
            }
            finalArt.setRating(parseInt(node.get(RATING).toString()));
            return finalArt;
        }
        else {
            final MuseumDataAccessObject daoBj = new MuseumDataAccessObject();
            return daoBj.getArtworkById(id);
        }
    }

    /**
     * Precondition: jsonFile is not empty.
     * @param id is the unique id of the artwork
     * @return whether artwork with id is in the database.
     * @throws IOException exception
     */
    @Override
    public boolean contains(String id) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode rootNode = objectMapper.readTree(jsonFile);
        final ArrayNode artworks = (ArrayNode) rootNode.get(ARTWORKS);
        for (JsonNode artwork: artworks) {
            if (artwork.get(ID).asText().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Artwork> getAllFavorites() {
        final List<Artwork> favoriteArtworks = new ArrayList<>();
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode rootNode = objectMapper.readTree(jsonFile);

            final ArrayNode artworksArray = (ArrayNode) rootNode.get(ARTWORKS);
            for (JsonNode node : artworksArray) {
                if (node.get(FAVORITE).asBoolean()) {
                    favoriteArtworks.add(convertJsonToArtwork(node));
                }
            }
        }
        catch (IOException event) {
            event.printStackTrace();
        }
        return favoriteArtworks;
    }

    private Artwork convertJsonToArtwork(JsonNode node) {
        try {
            final String id = node.get(ID).asText();

            final Artwork artwork = getArtworkById(id);

            return artwork;
        }
        catch (Exception event) {
            event.printStackTrace();
            return null;
        }
    }

    /**
     * Get comments.
     * @param artwork artwork
     * @return comment list
     * @throws IOException exception
     */
    public List<String> getCommentsForArtwork(Artwork artwork) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode rootNode = objectMapper.readTree(jsonFile);
        if (contains(artwork.getId())) {
            final JsonNode node = rootNode.get(artwork.getId());
            final ArrayNode comments = (ArrayNode) node.get(COMMENTS);
            final List<String> commentList = new ArrayList<>();
            for (JsonNode comment: comments) {
                commentList.add(comment.toString());
            }
            return commentList;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Artwork> getCommentedArtworks() {
        final List<Artwork> commentedArtworks = new ArrayList<>();
        for (Artwork artwork : artworks.values()) {
            if (!artwork.getComments().isEmpty()) {
                commentedArtworks.add(artwork);
            }
        }
        return commentedArtworks;
    }

    /**
     * Get rating.
     * @param id id
     * @return int
     * @throws IOException excpetion
     */
    public int getRating(String id) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode rootNode = objectMapper.readTree(jsonFile);
        if (contains(id)) {
            final JsonNode node = rootNode.get(id);
            if (node.has(RATING) && node.get(RATING).isInt()) {
                return node.get(RATING).asInt();
            }
        }
        return 0;
    }

    @Override
    public List<Artwork> getRatedArtworks() {
        final List<Artwork> ratedArtworks = new ArrayList<>();
        for (Artwork artwork : artworks.values()) {
            if (artwork.getRating() > 0) {
                ratedArtworks.add(artwork);
            }
        }
        return ratedArtworks;
    }

    @Override
    public void updateRating(String id, int rating) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode rootNode = objectMapper.readTree(jsonFile);
            if (contains(id)) {
                JsonNode node = null;
                for (JsonNode art : rootNode.get(ARTWORKS)) {
                    if (art.get(ID).asText().equals(id)) {
                        node = art;
                        break;
                    }
                }
                ((ObjectNode) node).put(RATING, rating);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
            }
            else {
                final Artwork artwork = getArtworkById(id);
                artwork.setRating(rating);
                save(artwork);
            }
        }
        catch (IOException event) {
            System.err.println(FILE_NOT_FOUND_ATTEMPTING_TO_USE_A_DEFAULT_FILE);
        }
    }

    @Override
    public List<Artwork> searchArtwork(String searchMessage) {
        return List.of();
    }

    @Override
    public Artwork getSelectedArtwork(Artwork artwork) throws IOException {
        return null;
    }
}
