package use_case.search;

import java.io.IOException;

import data.MuseumDataAccessObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.*;

import entities.*;
import view.*;
import app.Main;

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

    public SearchInteractor(MuseumDataAccessObject museumDataAccessObject, SearchOutputBoundary searchOutputBoundary) {
    }

    // TODO: get the IDs of the search query etc then make an "object" API call to get attributes of the Artwork
    public static List<Artwork> searchArtwork(String query){
        final OkHttpClient client = new OkHttpClient();
        // search?q=%s&username=%s to add more
        List<JSONObject> responses = new ArrayList<>();
        List<entities.Artwork> artworks = new ArrayList<>();
        final Request reqMet = new Request.Builder().url((String.format("%s/search?q=%s", QUERY_MET, query))).build();
        /**
         final Request reqChi = new Request.Builder().url((String.format("%s/search?q=%s", QUERY_CHI, query))).build();
         final Request reqHar = new Request.Builder()
         .url((String.format("%s/object?apikey=%s?keyword=%s", QUERY_HAR, HAR_TOKEN, query))).build();
         final Request reqEur = new Request.Builder()
         .url((String.format("%s/search?apikey=%s?query=%s", QUERY_EUR, EUR_TOKEN, query))).build();
         */

        try {
            final Response responseMet = client.newCall(reqMet).execute();
            // TODO: other three museums
            /**
             final Response responseChi = client.newCall(reqChi).execute();
             final Response responseHar = client.newCall(reqHar).execute();
             final Response responseEur = client.newCall(reqEur).execute();
             */
            final JSONObject artsM = new JSONObject(responseMet.body().string());
            // final JSONObject artsC = new JSONObject(responseChi.body().string());
            // final JSONObject artsH = new JSONObject(responseHar.body().string());
            // final JSONObject artsE = new JSONObject(responseEur.body().string());
            responses.add(artsM);
            // responses.add(artsC);
            // responses.add(artsH);
            // responses.add(artsE);
            for (JSONObject resp: responses) {
                // change from the list of IDs to actual artworks; if there are too many entries, cap the amount
                if ((int) JSONObject.stringToValue(resp.get("total").toString()) > 10) {
                    Random rand = new Random();
                    for (int i = 0; i < 1; i++) {
                        int id = rand.nextInt((int) JSONObject.stringToValue(resp.get("total").toString()));
                        final Request artReq = new Request.Builder().url(String.format("%s/objects/%d", QUERY_MET, (int) ((JSONArray) resp.get("objectIDs")).get(id))).build();
                        final Response artResp = client.newCall(artReq).execute();
                        final JSONObject artObj = new JSONObject(artResp.body().string());
                        artworks.add(ArtworkFactory.createArtwork(artObj.get("title").toString()
                                , artObj.get("artistDisplayName").toString(),
                                artObj.get("period").toString(), artObj.get("repository").toString(),
                                artObj.get("primaryImage").toString(), artObj.get("tags").toString()));
                    }
                }
                else {
                    for (int i = 0; i < (int) JSONObject.stringToValue(resp.get("total").toString()); i++) {
                        final Request artReq = new Request.Builder().url(String.format("%s/objects/%d", QUERY_MET, (int) ((JSONArray) resp.get("objectIDs")).get(i))).build();
                        final Response artResp = client.newCall(artReq).execute();
                        final JSONObject artObj = new JSONObject(artResp.body().string());
                        artworks.add(ArtworkFactory.createArtwork(artObj.get("title").toString()
                                , artObj.get("artistDisplayName").toString(),
                                artObj.get("period").toString(), artObj.get("repository").toString(),
                                artObj.get("primaryImage").toString(), artObj.get("tags").toString()));
                    }
                }
            }


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

    @Override
    public void execute(SearchInputData searchInputData) {
        //need to add later
    }
}
