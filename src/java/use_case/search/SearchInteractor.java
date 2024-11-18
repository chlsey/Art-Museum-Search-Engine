package use_case.search;

import java.io.IOException;

import data.MuseumDataAccessObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchInteractor implements SearchInputBoundary {
    private static final String QUERY_CHI = "https://api.artic.edu/api/v1/artworks";
    private static final String QUERY_MET = "https://collectionapi.metmuseum.org/public/collection/v1";
    private static final String QUERY_HAR = "https://api.harvardartmuseums.org/";
    private static final String HAR_TOKEN = "652c0f7b-48d9-4f4d-b1ca-ddfb582c9c12";
    private static final String QUERY_EUR = "https://api.europeana.eu/record/v2/search.json";
    private static final String EUR_TOKEN = "kerambleat";
    private static final String MESSAGE = "message";

    public SearchInteractor(MuseumDataAccessObject museumDataAccessObject, SearchOutputBoundary searchOutputBoundary) {}

    // TODO: get the IDs of the search query etc then make an "object" API call to get attributes of the Artwork
    public static List<Artwork> searchArtwork(String query, String spec){

        String filters = "Artwork";
        if (spec != null) {
            filters = spec;
        }

        final OkHttpClient client = new OkHttpClient();
        // search?q=%s&username=%s to add more
        List<Artwork> artworks = new ArrayList<>();
        Request reqMet = reqMetBuilder(query, filters);
        Request reqChi = reqChiBuilder(query, filters);


        /**
         final Request reqHar = new Request.Builder()
         .url((String.format("%s/object?apikey=%s?keyword=%s", QUERY_HAR, HAR_TOKEN, query))).build();
         final Request reqEur = new Request.Builder()
         .url((String.format("%s/search?apikey=%s?query=%s", QUERY_EUR, EUR_TOKEN, query))).build();
         */

        try {
            final Response responseMet = client.newCall(reqMet).execute();
            // TODO: other three museums
            final Response responseChi = client.newCall(reqChi).execute();
            /**
             final Response responseHar = client.newCall(reqHar).execute();
             final Response responseEur = client.newCall(reqEur).execute();
             */
            final JSONObject artsM = new JSONObject(responseMet.body().string());
            final JSONObject artsC = new JSONObject(responseChi.body().string());
            // final JSONObject artsH = new JSONObject(responseHar.body().string());
            // final JSONObject artsE = new JSONObject(responseEur.body().string());

            // Add met museum responses. if there are more than 10 images, add 10 randomly
            if ((int) JSONObject.stringToValue(artsM.get("total").toString()) > 10) {
                Random rand = new Random();
                for (int i = 0; i < 10; i++) {
                    int id = rand.nextInt((int) JSONObject.stringToValue(artsM.get("total").toString()));
                    metmuseumHandler(client, artworks, artsM, id);
                }
            }
            else {
                for (int i = 0; i < (int) JSONObject.stringToValue(artsM.get("total").toString()); i++) {
                    metmuseumHandler(client, artworks, artsM, i);
                }
            }
            CmuseumHandler(artworks, artsC);

            /*artworks.add(ArtworkFactory.createArtwork(artM.get("title"), artM.get("artistDisplayName"),
                    artM.get("period"), artM.get("repository"), artM.get("primaryImage"), artM.get("tags"), "genome")); */
            //String title, String artistName, String timePeriod,
            //                                        String gallery, String imageUrl,
            //                                        String[] keyWords
            return artworks;

        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    private static void metmuseumHandler(OkHttpClient client, List<Artwork> artworks, JSONObject resp, int i) throws IOException {
        final Request artReq = new Request.Builder().url(String.format("%s/objects/%d", QUERY_MET, (int) ((JSONArray) resp.get("objectIDs")).get(i))).build();
        final Response artResp = client.newCall(artReq).execute();
        final JSONObject artObj = new JSONObject(artResp.body().string());
        String[] properties = {"title", "artistDisplayName", "period", "repository", "primaryImage", "tags"};
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

        artworks.add(ArtworkFactory.createArtwork(artObj.get("title").toString()
                , artObj.get("artistDisplayName").toString(),
                artObj.get("period").toString(), artObj.get("repository").toString(),
                artObj.get("primaryImage").toString(), artObj.get("tags").toString()));
    }

    private static Request reqMetBuilder(String query, String spec) {
        if (spec.equals("Artist")) {
            return new Request.Builder().url((String.format("%s/search?q=%s?artistOrCulture=true", QUERY_MET, query))).build();
        } else {
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
        String[] properties = {"title", "artist_title", "date_display", "image_id"};
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

            artworks.add(ArtworkFactory.createArtwork(artIndiv.get("title").toString()
                    , artIndiv.get("artist_title").toString(),
                    artIndiv.get("date_display").toString(), "Art Institute of Chicago",
                    artIndiv.get("image_id").toString(), ""));
        }



    }

    @Override
    public List<Artwork> execute(SearchInputData searchInputData) {
        //need to add later
        return null;
    }
}