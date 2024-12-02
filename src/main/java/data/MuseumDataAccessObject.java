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
import use_case.comment.CommentDataAccessInterface;
import use_case.filter.FilterDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

/**
 * Museum data access object class.
 */
public class MuseumDataAccessObject implements SearchDataAccessInterface, CommentDataAccessInterface,
        FilterDataAccessInterface {
    private static final String QUERY_CHI = "https://api.artic.edu/api/v1/artworks";
    private static final String QUERY_MET = "https://collectionapi.metmuseum.org/public/collection/v1";
    private static final int TEN = 10;
    private static final int TWENTY = 20;
    private static final String TOTAL = "total";
    private static final String TITLE = "title";
    private String spec;

    /**
     * Change filter.
     * @param spec the spec
     */
    public void changeFilter(String spec) {
        this.spec = spec;
    }

    /**
     * Search artwork.
     * @param query query
     * @return artwork
     */
    public List<Artwork> searchArtwork(String query) {
        final OkHttpClient client = new OkHttpClient();
        final List<Artwork> artworks = new ArrayList<>();
        final Request reqMet = reqMetBuilder(query, spec);
        final Request reqChi = reqChiBuilder(query, spec);

        try {
            final Response responseMet = client.newCall(reqMet).execute();
            final Response responseChi = client.newCall(reqChi).execute();

            final JSONObject artsM = new JSONObject(responseMet.body().string());
            final JSONObject artsC = new JSONObject(responseChi.body().string());

            // since the Chicago museum doesn't seem to have a lot of images..
            if (!spec.equals("Hasimages")) {
                CmuseumHandler(artworks, artsC);
            }

            // Add met museum responses. if there are more than 10 images, add 10 randomly
            if ((int) JSONObject.stringToValue(artsM.get(TOTAL).toString()) > TEN) {
                for (int i = 0; i < Math.min((int) JSONObject.stringToValue(artsM.get(TOTAL).toString()),
                        TWENTY); i++) {
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
        String[] properties = {TITLE, "artistDisplayName", "period", "repository", "primaryImage", "tags",
                "department", "medium", "classification", "objectName", "artistPrefix"};
        for (String property: properties) {
            if (!artObj.has(property) || artObj.get(property) == JSONObject.NULL) {
                if (property.equals("primaryImage")) {
                    artObj.put("primaryImage", "src/images/noimg.png");
                }
                else {
                    artObj.put(property, property + " Not found");
                }
                hasImage = false;
            }
        }
        System.out.println(artObj.get("primaryImage"));
        if (artObj.get("primaryImage").toString().isEmpty()) {
            artObj.put("primaryImage", "src/images/noimg.png");
            hasImage = false;
        }

        if (hasImage) {
            final Artwork result = ArtworkFactory.createArtwork(artObj.get(TITLE).toString(),
                artObj.get("artistDisplayName").toString(),
                artObj.get("period").toString(), artObj.get("repository").toString(),
                artObj.get("primaryImage").toString(), artObj.get("tags").toString(),
                String.format("%s, %s, %s, %s %s", artObj.get("department"), artObj.get("medium"),
                        artObj.get("classification"), artObj.get("objectName"), artObj.get("artistPrefix")),
                            "MET-" + artObj.get("objectID").toString());

            artworks.add(result);
        }
    }

    private static Request reqMetBuilder(String query, String spec) {
        if ("Artist".equals(spec)) {
            return new Request.Builder()
                    .url(String.format("%s/search?q=%s?artistOrCulture=true", QUERY_MET, query))
                    .build();
        }
        else if ("Highlight".equals(spec)) {
            return new Request.Builder()
                    .url(String.format("%s/search?q=%s?isHighlight=true", QUERY_MET, query))
                    .build();
        }
        else if ("Onview".equals(spec)) {
            return new Request.Builder().url(String.format("%s/search?q=%s?isOnView=true", QUERY_MET, query)).build();
        }
        else if ("Hasimages".equals(spec)) {
            return new Request.Builder()
                    .url(String.format("%s/search?q=%s?hasImages=true", QUERY_MET, query))
                    .build();
        }
        else {
            return new Request.Builder().url(String.format("%s/search?q=%s", QUERY_MET, query)).build();
        }
    }

    private static Request reqChiBuilder(String query, String spec) {
        return new Request.Builder().url(String.format("%s/search?fields=id,title,artist_title,description,"
                + "date_display,image_id?q=%s", QUERY_CHI, query)).build();
    }

    private static void CmuseumHandler(List<Artwork> artworks, JSONObject artResp) throws IOException {
        final JSONArray artObj = new JSONArray(artResp.get("java/data").toString());
        final JSONObject link = new JSONObject(artResp.get("config").toString());
        final String[] properties = {"id", TITLE, "artist_title", "date_display", "image_id", "description"};
        for (Integer i = 0; i < TEN; i++) {
            boolean hasImage = true;
            final JSONObject artIndiv = artObj.getJSONObject(i);
            for (String property : properties) {
                if (!artIndiv.has(property) || artIndiv.get(property) == null) {
                    if ("image_id".equals(property)) {
                        artIndiv.put("image_id", "src/images/noimg.png");
                        hasImage = false;
                    }
                    else {
                        artIndiv.put(property, property + " Not found");
                    }
                }
            }
            if (hasImage) {
                // format
                artIndiv.put("image_id", String.format("%s/%s/full/843,/0/default.jpg", link.get("iiif_url"),
                        artIndiv.get("image_id").toString()));
            }

            final Document desc = Jsoup.parse(artIndiv.get("description").toString());

            final Artwork result = ArtworkFactory.createArtwork(artIndiv.get(TITLE).toString(),
                    artIndiv.get("artist_title").toString(),
                    artIndiv.get("date_display").toString(), "Art Institute of Chicago",
                    artIndiv.get("image_id").toString(), "No keywords",
                    desc.body().text(), "CHI-" + artIndiv.get("id").toString());

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

        if (id.startsWith("MET-")) {
            final String trimmedId = id.substring(4);
            final Request url = new Request.Builder().url(String.format("%s/objects/%s",
                    QUERY_MET, trimmedId)).build();
            final Response artResp = client.newCall(url).execute();

            if (artResp.isSuccessful()) {
                final JSONObject artObj = new JSONObject(artResp.body().string());
                artwork = parseMetMuseumArtwork(artObj);
            }
        }

        else if (id.startsWith("CHI-")) {
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
        final String[] properties = {TITLE, "artistDisplayName", "period", "repository", "primaryImage", "tags",
                "department", "medium", "classification", "objectName", "artistPrefix"};
        for (String property : properties) {
            if (!artObj.has(property)) {
                if ("primaryImage".equals(property)) {
                    artObj.put("primaryImage", "src/images/noimg.png");
                }
                else {
                    artObj.put(property, property + " Not found");
                }
            }
        }
        if (artObj.get("primaryImage").equals("")) {
            artObj.put("primaryImage", "src/images/noimg.png");
        }
        final String keywords;
        if (artObj.has("tags") && artObj.get("tags") instanceof JSONArray) {
            final JSONArray tagsArray = artObj.getJSONArray("tags");
            keywords = tagsArray.join(", ").replaceAll("\"", "");
        }
        else {
            keywords = "No keywords available";
        }

        return ArtworkFactory.createArtwork(
                artObj.get(TITLE).toString(),
                artObj.get("artistDisplayName").toString(),
                artObj.get("period").toString(),
                artObj.get("repository").toString(),
                artObj.get("primaryImage").toString(),
                keywords,
                String.format("%s, %s, %s, %s %s",
                        artObj.get("department").toString(),
                        artObj.get("medium").toString(),
                        artObj.get("classification").toString(),
                        artObj.get("objectName").toString(),
                        artObj.get("artistPrefix").toString()),
                "MET-" + artObj.get("objectID").toString()
        );
    }

    private Artwork parseChicagoArtwork(JSONObject artObj) {
        final String[] properties = {TITLE, "artist_title", "date_display", "image_id", "description"};
        for (String property : properties) {
            if (!artObj.has(property)) {
                if ("image_id".equals(property)) {
                    artObj.put(property, "src/images/noimg.png");
                }
                else {
                    artObj.put(property, property + " Not found");
                }
            }
        }

        String imageUrl = "src/images/noimg.png";
        if (artObj.has("image_id") && !artObj.getString("image_id").isEmpty()) {
            final JSONObject config = artObj.getJSONObject("config");
            final String iiifUrl = config.optString("iiif_url", "");
            imageUrl = String.format("%s/%s/full/843,/0/default.jpg", iiifUrl, artObj.getString("image_id"));
        }

        final Document desc = Jsoup.parse(artObj.optString("description", "Description not found"));

        return ArtworkFactory.createArtwork(
                artObj.get(TITLE).toString(),
                artObj.get("artist_title").toString(),
                artObj.get("date_display").toString(),
                "Art Institute of Chicago",
                imageUrl,
                "No keywords",
                desc.body().text(),
                "CHI-" + artObj.get("id").toString()
        );
    }
}
