package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import entities.Artwork;
import entities.ArtworkFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.click_art.ClickArtDataAccessInterface;
import use_case.comment.CommentDataAccessInterface;
import use_case.filter.FilterDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

/**
 * Museum Data Access Object.
 */

public class MuseumDataAccessObject implements SearchDataAccessInterface, CommentDataAccessInterface,
        FilterDataAccessInterface, ClickArtDataAccessInterface {
    private static final String QUERY_CHI = "https://api.artic.edu/api/v1/artworks";
    private static final String QUERY_MET = "https://collectionapi.metmuseum.org/public/collection/v1";
    private static final String CHI = "CHI-";
    private static final String IMAGE_ID = "image_id";
    private static final String ID = "id";
    private static final int TEN = 10;
    private static final String ARTIST_TITLE = "artist_title";
    private static final String DATE_DISPLAY = "date_display";
    private static final String DESCRIPTION = "description";
    private static final String ARTIST = "Artist";
    private static final String HIGHLIGHT = "Highlight";
    private static final String ONVIEW = "Onview";
    private static final String HASIMAGES = "Hasimages";
    private static final String NOT_FOUND = " Not found";
    private static final String SRC_IMAGES_NOIMG_PNG = "src/images/noimg.png";
    private static final String PRIMARY_IMAGE = "primaryImage";
    private static final String TITLE = "title";
    private static final String ARTIST_DISPLAY_NAME = "artistDisplayName";
    private static final String PERIOD = "period";
    private static final String REPOSITORY = "repository";
    private static final String TAGS = "tags";
    private static final String DEPARTMENT = "department";
    private static final String MEDIUM = "medium";
    private static final String CLASSIFICATION = "classification";
    private static final String OBJECT_NAME = "objectName";
    private static final String ARTIST_PREFIX = "artistPrefix";
    private static final String TOTAL = "total";
    private static final int TWENTY = 20;
    private String spec;

    /**
     * Change the filter.
     * @param specification String
     */
    public void changeFilter(String specification) {
        this.spec = specification;
    }

    /**
     * Token required and expired for these museum APIs.
     * final Request reqEur = new Request.Builder()
     * .url((String.format("%s/search?apikey=%s?query=%s", QUERY_EUR, EUR_TOKEN, query))).build();
     * final Request reqHar = new Request.Builder()
     * .url((String.format("%s/object?apikey=%s?keyword=%s", QUERY_HAR, HAR_TOKEN, query))).build();
     * @param query String
     * @return A list of searched artwork.
     * @throws RuntimeException an exception.
     */
    public List<Artwork> searchArtwork(String query) {

        final OkHttpClient client = new OkHttpClient();
        // search?q=%s&username=%s to add more
        final List<Artwork> artworks = new ArrayList<>();
        final Request reqMet = reqMetBuilder(query, spec);
        final Request reqChi = reqChiBuilder(query, spec);

        try {
            final Response responseMet = client.newCall(reqMet).execute();
            final Response responseChi = client.newCall(reqChi).execute();

            final JSONObject artsM = new JSONObject(responseMet.body().string());
            final JSONObject artsC = new JSONObject(responseChi.body().string());

            if (!spec.equals(HASIMAGES)) {
                cmuseumHandler(artworks, artsC);
            }

            // Add met museum responses. if there are more than 10 images, add 10 randomly
            if ((int) JSONObject.stringToValue(artsM.get(TOTAL).toString()) > TEN) {
                for (int i = 0; i < Math.min((int) JSONObject.stringToValue(artsM.get(TOTAL).toString()),
                        TWENTY); i++) {
                    // metmuseumHandler(client, artworks, artsM, id);
                    metmuseumHandler(client, artworks, artsM, i);
                }
            }
            else {
                for (int i = 0; i < (int) JSONObject.stringToValue(artsM.get(TOTAL).toString()); i++) {
                    metmuseumHandler(client, artworks, artsM, i);
                }
            }

            return artworks;

        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    private static void metmuseumHandler(OkHttpClient client, List<Artwork> artworks, JSONObject resp, int index)
            throws IOException {
        final Request artReq = new Request.Builder().url(String.format("%s/objects/%d", QUERY_MET, ((JSONArray)
                resp.get("objectIDs")).getInt(index))).build();
        final Response artResp = client.newCall(artReq).execute();
        final JSONObject artObj = new JSONObject(artResp.body().string());
        boolean hasImage = true;
        final String[] properties = {TITLE, ARTIST_DISPLAY_NAME, PERIOD, REPOSITORY, PRIMARY_IMAGE, TAGS, DEPARTMENT,
                                     MEDIUM, CLASSIFICATION, OBJECT_NAME, ARTIST_PREFIX};
        for (String property: properties) {
            if (!artObj.has(property) || artObj.get(property) == JSONObject.NULL) {
                if (property.equals(PRIMARY_IMAGE)) {
                    artObj.put(PRIMARY_IMAGE, SRC_IMAGES_NOIMG_PNG);
                }
                else {
                    artObj.put(property, property + NOT_FOUND);
                }
                hasImage = false;
            }
        }
        // changed
        System.out.println(artObj.get(PRIMARY_IMAGE));
        if (artObj.get(PRIMARY_IMAGE).toString().isEmpty()) {
            artObj.put(PRIMARY_IMAGE, SRC_IMAGES_NOIMG_PNG);
            hasImage = false;
        }

        if (hasImage) {
            final Artwork result = ArtworkFactory.createArtwork(artObj.get(TITLE).toString(),
                    artObj.get(ARTIST_DISPLAY_NAME).toString(),
                    artObj.get(PERIOD).toString(), artObj.get(REPOSITORY).toString(),
                    artObj.get(PRIMARY_IMAGE).toString(), artObj.get(TAGS).toString(),
                    String.format("%s, %s, %s, %s %s", artObj.get(DEPARTMENT), artObj.get(MEDIUM),
                            artObj.get(CLASSIFICATION), artObj.get(OBJECT_NAME), artObj.get(ARTIST_PREFIX)),
                    artObj.get("objectID").toString());

            artworks.add(result);
        }
    }

    private static Request reqMetBuilder(String query, String spec) {
        final Request request;
        if (spec.equals(ARTIST)) {
            request = new Request.Builder().url(String.format("%s/search?q=%s?artistOrCulture=true", QUERY_MET, query))
                    .build();
        }
        else if (spec.equals(HIGHLIGHT)) {
            request = new Request.Builder().url(String.format("%s/search?q=%s?isHighlight=true", QUERY_MET, query))
                    .build();
        }
        else if (spec.equals(ONVIEW)) {
            request = new Request.Builder().url(String.format("%s/search?q=%s?isOnView=true", QUERY_MET, query))
                    .build();
        }
        else if (spec.equals(HASIMAGES)) {
            request = new Request.Builder().url(String.format("%s/search?q=%s?hasImages=true", QUERY_MET, query))
                    .build();
        }
        else {
            request = new Request.Builder().url(String.format("%s/search?q=%s", QUERY_MET, query)).build();
        }
        return request;
    }

    private static Request reqChiBuilder(String query, String spec) {
        // Chicago institute API does not seem to support filtered search.
        return new Request.Builder().url(String
                .format("%s/search?fields=id,title,artist_title,description,date_display,image_id?q=%s",
                        QUERY_CHI, query)).build();
    }

    private static void cmuseumHandler(List<Artwork> artworks, JSONObject artResp) throws IOException {
        // for (JSONObject art: new JSONObject(artResp.body()) {};
        final JSONArray artObj = new JSONArray(artResp.get("data").toString());
        final JSONObject link = new JSONObject(artResp.get("config").toString());
        final String[] properties = {ID, TITLE, ARTIST_TITLE, DATE_DISPLAY, IMAGE_ID, DESCRIPTION};
        for (Integer i = 0; i < TEN; i++) {
            boolean hasImage = true;
            final JSONObject artIndiv = artObj.getJSONObject(i);
            for (String property : properties) {
                if (!artIndiv.has(property) || artIndiv.get(property) == null) {
                    if (property.equals(IMAGE_ID)) {
                        artIndiv.put(IMAGE_ID, SRC_IMAGES_NOIMG_PNG);
                        hasImage = false;
                    }
                    else {
                        artIndiv.put(property, property + NOT_FOUND);
                    }
                }
            }
            if (hasImage) {
                // format
                artIndiv.put(IMAGE_ID, String.format("%s/%s/full/843,/0/default.jpg", link.get("iiif_url"),
                        artIndiv.get(IMAGE_ID).toString()));
            }

            final Document desc = Jsoup.parse(artIndiv.get(DESCRIPTION).toString());

            final Artwork result = ArtworkFactory.createArtwork(artIndiv.get(TITLE).toString(),
                    artIndiv.get(ARTIST_TITLE).toString(),
                    artIndiv.get(DATE_DISPLAY).toString(), "Art Institute of Chicago",
                    artIndiv.get(IMAGE_ID).toString(), "No keywords",
                    desc.body().text(), CHI + artIndiv.get(ID).toString());

            artworks.add(result);
        }
    }

    @Override
    public void addCommentToArtwork(Artwork artwork, String comment) throws IOException {
    }

    @Override
    public Artwork getArtworkById(String id) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        Artwork artwork = null;

        // Met
        if (id.startsWith("MET-")) {
            final String trimmedId = id.substring(4);
            final Request url = new Request.Builder().url(String.format("%s/objects/%s", QUERY_MET, trimmedId)).build();
            final Response artResp = client.newCall(url).execute();

            if (artResp.isSuccessful()) {
                final JSONObject artObj = new JSONObject(artResp.body().string());
                artwork = parseMetMuseumArtwork(artObj);
            }
        }
        // Chicago
        else if (id.startsWith(CHI)) {
            final String trimmedId = id.substring(4);
            final String url = String.format("%s/%s", QUERY_CHI, trimmedId);
            final Request request = new Request.Builder().url(url).build();
            final Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                final JSONObject artObj = new JSONObject(response.body().string());
                artwork = parseChicagoArtwork(artObj);
            }
        }

        return artwork;
    }

    @Override
    public List<Artwork> getCommentedArtworks() {
        return List.of();
    }

    // Helper to parse Met Museum individual artworks
    private Artwork parseMetMuseumArtwork(JSONObject artObj) {
        final String[] properties = {TITLE, ARTIST_DISPLAY_NAME, PERIOD, REPOSITORY, PRIMARY_IMAGE, TAGS,
                                     DEPARTMENT, MEDIUM, CLASSIFICATION, OBJECT_NAME, ARTIST_PREFIX};
        for (String property : properties) {
            if (!artObj.has(property)) {
                if (PRIMARY_IMAGE.equals(property)) {
                    artObj.put(PRIMARY_IMAGE, SRC_IMAGES_NOIMG_PNG);
                }
                else {
                    artObj.put(property, property + NOT_FOUND);
                }
            }
        }
        // changed
        if (artObj.get(PRIMARY_IMAGE).equals("")) {
            artObj.put(PRIMARY_IMAGE, SRC_IMAGES_NOIMG_PNG);
        }
        // Convert tags JSONArray to a String
        final String keywords;
        if (artObj.has(TAGS) && artObj.get(TAGS) instanceof JSONArray) {
            final JSONArray tagsArray = artObj.getJSONArray(TAGS);
            keywords = tagsArray.join(", ").replaceAll("\"", "");
        }
        else {
            keywords = "No keywords available";
        }

        return ArtworkFactory.createArtwork(
                artObj.get(TITLE).toString(),
                artObj.get(ARTIST_DISPLAY_NAME).toString(),
                artObj.get(PERIOD).toString(),
                artObj.get(REPOSITORY).toString(),
                artObj.get(PRIMARY_IMAGE).toString(),
                keywords,
                String.format("%s, %s, %s, %s %s",
                        artObj.get(DEPARTMENT).toString(),
                        artObj.get(MEDIUM).toString(),
                        artObj.get(CLASSIFICATION).toString(),
                        artObj.get(OBJECT_NAME).toString(),
                        artObj.get(ARTIST_PREFIX).toString()),
                "MET-" + artObj.get("objectID").toString()
        );
    }

    // Helper to parse Art Institute of Chicago individual artworks
    private Artwork parseChicagoArtwork(JSONObject artObj) {
        final String[] properties = {TITLE, ARTIST_TITLE, DATE_DISPLAY, IMAGE_ID, DESCRIPTION};
        for (String property : properties) {
            if (!artObj.has(property)) {
                if (IMAGE_ID.equals(property)) {
                    artObj.put(property, SRC_IMAGES_NOIMG_PNG);
                }
                else {
                    artObj.put(property, property + NOT_FOUND);
                }
            }
        }

        String imageUrl = SRC_IMAGES_NOIMG_PNG;
        if (artObj.has(IMAGE_ID) && !artObj.getString(IMAGE_ID).isEmpty()) {
            final JSONObject config = artObj.getJSONObject("config");
            final String iiifUrl = config.optString("iiif_url", "");
            imageUrl = String.format("%s/%s/full/843,/0/default.jpg", iiifUrl, artObj.getString(IMAGE_ID));
        }

        final Document desc = Jsoup.parse(artObj.optString(DESCRIPTION, "Description not found"));

        return ArtworkFactory.createArtwork(
                artObj.get(TITLE).toString(),
                artObj.get(ARTIST_TITLE).toString(),
                artObj.get(DATE_DISPLAY).toString(),
                "Art Institute of Chicago",
                imageUrl,
                "No keywords",
                desc.body().text(),
                CHI + artObj.get(ID).toString()
        );
    }

    /**
     * Get the selected artwork.
     * @param artwork Artwork
     * @return the selected one.
     * @throws IOException an exception.
     */
    public Artwork getSelectedArtwork(Artwork artwork) throws IOException {
        return getArtworkById(artwork.getId());
    }
}
