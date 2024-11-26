package data;

import entities.Artwork;
import entities.ArtworkFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import use_case.comment.CommentDataAccessInterface;
import use_case.filter.FilterDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MuseumDataAccessObject implements SearchDataAccessInterface, CommentDataAccessInterface, FilterDataAccessInterface {
    private static final String QUERY_CHI = "https://api.artic.edu/api/v1/artworks";
    private static final String QUERY_MET = "https://collectionapi.metmuseum.org/public/collection/v1";
    private String spec;

    public void changeFilter(String spec) {
        this.spec = spec;
    }

    public  List<Artwork> searchArtwork(String query){


        final OkHttpClient client = new OkHttpClient();
        // search?q=%s&username=%s to add more
        List<Artwork> artworks = new ArrayList<>();
        Request reqMet = reqMetBuilder(query, spec);
        Request reqChi = reqChiBuilder(query, spec);


        /**
         * token required and expired for these museum APIs
         final Request reqEur = new Request.Builder()
         .url((String.format("%s/search?apikey=%s?query=%s", QUERY_EUR, EUR_TOKEN, query))).build();
         final Request reqHar = new Request.Builder()
         .url((String.format("%s/object?apikey=%s?keyword=%s", QUERY_HAR, HAR_TOKEN, query))).build();
         */

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
            if ((int) JSONObject.stringToValue(artsM.get("total").toString()) > 10) {
                for (int i = 0; i < Math.min((int) JSONObject.stringToValue(artsM.get("total").toString()), 20); i++) {
                    // metmuseumHandler(client, artworks, artsM, id);
                    metmuseumHandler(client, artworks, artsM, i);
                }
            }
            else {
                for (int i = 0; i < (int) JSONObject.stringToValue(artsM.get("total").toString()); i++) {
                    metmuseumHandler(client, artworks, artsM, i);
                }
            }

            return artworks;

        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    private static void metmuseumHandler(OkHttpClient client, List<Artwork> artworks, JSONObject resp, int i) throws IOException {
        final Request artReq = new Request.Builder().url(String.format("%s/objects/%d", QUERY_MET, ((JSONArray) resp.get("objectIDs")).getInt(i))).build();
        final Response artResp = client.newCall(artReq).execute();
        final JSONObject artObj = new JSONObject(artResp.body().string());
        String[] properties = {"title", "artistDisplayName", "period", "repository", "primaryImage", "tags", "department", "medium", "classification", "objectName", "artistPrefix"};
        for (String property: properties) {
            if (!artObj.has(property)) {
                if (property.equals("primaryImage")) {
                    artObj.put("primaryImage", "src/images/noimg.png");
                }
                else {
                    artObj.put(property, property + " Not found");
                }
            }
        }
        // changed
        if (artObj.get("primaryImage") == "") {
            artObj.put("primaryImage", "src/images/noimg.png");
        }

        Artwork result = ArtworkFactory.createArtwork(artObj.get("title").toString()
                , artObj.get("artistDisplayName").toString(),
                artObj.get("period").toString(), artObj.get("repository").toString(),
                artObj.get("primaryImage").toString(), artObj.get("tags").toString(),
                String.format("%s, %s, %s, %s %s", artObj.get("department"), artObj.get("medium"), artObj.get("classification"), artObj.get("objectName"), artObj.get("artistPrefix")), "id placeholder");

        artworks.add(result);
    }

    private static Request reqMetBuilder(String query, String spec) {
        if (spec.equals("Artist")) {
            return new Request.Builder().url((String.format("%s/search?q=%s?artistOrCulture=true", QUERY_MET, query))).build();
        }
        else if (spec.equals("Highlight")) {
            return new Request.Builder().url((String.format("%s/search?q=%s?isHighlight=true", QUERY_MET, query))).build();
        }else if (spec.equals("Onview")) {
            return new Request.Builder().url((String.format("%s/search?q=%s?isOnView=true", QUERY_MET, query))).build();
        }else if (spec.equals("Hasimages")) {
            return new Request.Builder().url((String.format("%s/search?q=%s?hasImages=true", QUERY_MET, query))).build();
        }else {
            return new Request.Builder().url((String.format("%s/search?q=%s", QUERY_MET, query))).build();
        }
    }

    private static Request reqChiBuilder(String query, String spec) {
        // Chicago institute API does not seem to support filtered search.
        return new Request.Builder().url((String.format("%s/search?fields=id,title,artist_title,description,date_display,image_id?q=%s", QUERY_CHI, query))).build();
    }

    private static void CmuseumHandler(List<Artwork> artworks, JSONObject artResp) throws IOException {
        // for (JSONObject art: new JSONObject(artResp.body()) {};
        final JSONArray artObj = new JSONArray(artResp.get("data").toString());
        final JSONObject link = new JSONObject(artResp.get("config").toString());
        String[] properties = {"title", "artist_title", "date_display", "image_id", "description"};
        for (Integer i = 0; i < 10; i++) {
            boolean hasImage = true;
            JSONObject artIndiv = artObj.getJSONObject(i);
            for (String property: properties) {
                if (!artIndiv.has(property) || artIndiv.get(property) == null) {
                    if (property.equals("image_id")) {
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
                artIndiv.put("image_id", String.format("%s/%s/full/843,/0/default.jpg", link.get("iiif_url"), artIndiv.get("image_id").toString()));
            }

            Document desc = Jsoup.parse(artIndiv.get("description").toString());

            Artwork result = ArtworkFactory.createArtwork(artIndiv.get("title").toString()
                    , artIndiv.get("artist_title").toString(),
                    artIndiv.get("date_display").toString(), "Art Institute of Chicago",
                    artIndiv.get("image_id").toString(), "No keywords",
                    desc.body().text(), "id placeholder");

            artworks.add(result);
        }
    }

    @Override
    public void addCommentToArtwork(Artwork artwork, String comment) throws IOException {

    }

    @Override
    public Artwork getArtworkById(String id) throws IOException {
        return null;
    }
}

