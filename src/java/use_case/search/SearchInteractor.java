package use_case.search;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import data.MuseumDataAccessObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;


public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDataAccessObject;
    private final SearchOutputBoundary searchPresenter;
//    private static final String QUERY_CHI = "https://api.artic.edu/api/v1/artworks";
//    private static final String QUERY_MET = "https://collectionapi.metmuseum.org/public/collection/v1";


    public SearchInteractor(SearchDataAccessInterface searchDataAccessObject, SearchOutputBoundary searchPresenter) {
        this.searchDataAccessObject = searchDataAccessObject;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        final List<Artwork> ourartworks = searchDataAccessObject.searchArtwork(searchInputData.getSearchMessage());
        boolean failed = false;
        final SearchOutputData searchOutputData = new SearchOutputData(ourartworks,failed);
        searchPresenter.prepareSuccessView(searchOutputData);

//    public static List<Artwork> searchArtwork(String query, String spec){
//
//        String filters = "Artwork";
//        if (spec != null) {
//            filters = spec;
//        }
//
//        final OkHttpClient client = new OkHttpClient();
//        // search?q=%s&username=%s to add more
//        List<Artwork> artworks = new ArrayList<>();
//        Request reqMet = reqMetBuilder(query, filters);
//        Request reqChi = reqChiBuilder(query, filters);
//
//
//         /**
//          * token required and expired for these museum APIs
//          final Request reqEur = new Request.Builder()
//          .url((String.format("%s/search?apikey=%s?query=%s", QUERY_EUR, EUR_TOKEN, query))).build();
//          final Request reqHar = new Request.Builder()
//          .url((String.format("%s/object?apikey=%s?keyword=%s", QUERY_HAR, HAR_TOKEN, query))).build();
//         */
//
//        try {
//            final Response responseMet = client.newCall(reqMet).execute();
//            final Response responseChi = client.newCall(reqChi).execute();
//
//
//            final JSONObject artsM = new JSONObject(responseMet.body().string());
//            final JSONObject artsC = new JSONObject(responseChi.body().string());
//
//            int sizeMet = 20;
//
//            // since the Chicago museum doesn't seem to have a lot of images..
//            if (!spec.equals("Hasimages")) {
//                CmuseumHandler(artworks, artsC);
//                sizeMet = 10;
//            }
//
//            // Add met museum responses. if there are more than 10 images, add 10 randomly
//            if ((int) JSONObject.stringToValue(artsM.get("total").toString()) > 10) {
//                Random rand = new Random();
//                List<Integer> count = new ArrayList<>();
//                for (int i = 0; i < Math.min((int) JSONObject.stringToValue(artsM.get("total").toString()), 30); i++) {
//                    count.add(i);
//                }
//
//                for (int i = 0; i < sizeMet; i++) {
//                    int id = rand.nextInt(count.size());
//                    // metmuseumHandler(client, artworks, artsM, id);
//                    metmuseumHandler(client, artworks, artsM, id);
//                    count.remove(id);
//                }
//            }
//            else {
//                for (int i = 0; i < (int) JSONObject.stringToValue(artsM.get("total").toString()); i++) {
//                    metmuseumHandler(client, artworks, artsM, i);
//                }
//            }
//
//            /*artworks.add(ArtworkFactory.createArtwork(artM.get("title"), artM.get("artistDisplayName"),
//                    artM.get("period"), artM.get("repository"), artM.get("primaryImage"), artM.get("tags"), "genome")); */
//            //String title, String artistName, String timePeriod,
//            //                                        String gallery, String imageUrl,
//            //                                        String[] keyWords
//            return artworks;
//
//        }
//        catch (IOException | JSONException event) {
//            throw new RuntimeException(event);
//        }
//    }
//
//    private static void metmuseumHandler(OkHttpClient client, List<Artwork> artworks, JSONObject resp, int i) throws IOException {
//        final Request artReq = new Request.Builder().url(String.format("%s/objects/%d", QUERY_MET, ((JSONArray) resp.get("objectIDs")).getInt(i))).build();
//        final Response artResp = client.newCall(artReq).execute();
//        final JSONObject artObj = new JSONObject(artResp.body().string());
//        String[] properties = {"title", "artistDisplayName", "period", "repository", "primaryImage", "tags"};
//        for (String property: properties) {
//            if (!artObj.has(property)) {
//                if (property.equals("primaryImage")) {
//                    artObj.put("primaryImage", "src/images/noimg.png");
//                }
//                else {
//                    artObj.put(property, property + " Not found");
//                }
//            }
//        }
//        // changed
//        if (artObj.get("primaryImage") == "") {
//            artObj.put("primaryImage", "src/images/noimg.png");
//        }
//
//        artworks.add(ArtworkFactory.createArtwork(artObj.get("title").toString()
//                , artObj.get("artistDisplayName").toString(),
//                artObj.get("period").toString(), artObj.get("repository").toString(),
//                artObj.get("primaryImage").toString(), artObj.get("tags").toString()));
//    }
//
//    private static Request reqMetBuilder(String query, String spec) {
//        if (spec.equals("Artist")) {
//            return new Request.Builder().url((String.format("%s/search?q=%s?artistOrCulture=true", QUERY_MET, query))).build();
//        }
//        else if (spec.equals("Highlight")) {
//            return new Request.Builder().url((String.format("%s/search?q=%s?isHighlight=true", QUERY_MET, query))).build();
//        }else if (spec.equals("Onview")) {
//            return new Request.Builder().url((String.format("%s/search?q=%s?isOnView=true", QUERY_MET, query))).build();
//        }else if (spec.equals("Hasimages")) {
//            return new Request.Builder().url((String.format("%s/search?q=%s?hasImages=true", QUERY_MET, query))).build();
//        }else {
//            return new Request.Builder().url((String.format("%s/search?q=%s", QUERY_MET, query))).build();
//        }
//    }
//
//    private static Request reqChiBuilder(String query, String spec) {
//        // Chicago institute API does not seem to support filtered search.
//        return new Request.Builder().url((String.format("%s/search?fields=id,title,artist_title,description,date_display,image_id?q=%s", QUERY_CHI, query))).build();
//    }
//
//    private static void CmuseumHandler(List<Artwork> artworks, JSONObject artResp) throws IOException {
//        // for (JSONObject art: new JSONObject(artResp.body()) {};
//        final JSONArray artObj = new JSONArray(artResp.get("data").toString());
//        final JSONObject link = new JSONObject(artResp.get("config").toString());
//        String[] properties = {"title", "artist_title", "date_display", "image_id"};
//        for (Integer i = 0; i < 10; i++) {
//            boolean hasImage = true;
//            JSONObject artIndiv = artObj.getJSONObject(i);
//            for (String property: properties) {
//                if (!artIndiv.has(property) || artIndiv.get(property) == null) {
//                    if (property.equals("image_id")) {
//                        artIndiv.put("image_id", "src/images/noimg.png");
//                        hasImage = false;
//                    }
//                    else {
//                        artIndiv.put(property, property + " Not found");
//                    }
//                }
//            }
//            if (hasImage) {
//                // format
//                artIndiv.put("image_id", String.format("%s/%s/full/843,/0/default.jpg", link.get("iiif_url"), artIndiv.get("image_id").toString()));
//            }
//
//            artworks.add(ArtworkFactory.createArtwork(artIndiv.get("title").toString()
//                    , artIndiv.get("artist_title").toString(),
//                    artIndiv.get("date_display").toString(), "Art Institute of Chicago",
//                    artIndiv.get("image_id").toString(), ""));
//        }
//    }
//        StringBuilder artworks = new StringBuilder();
//        JPanel panelPictures = new JPanel();
//        for (Artwork art: ourartworks) {
//            artworks.append(art.getTitle() + "\n");
//            try {
//                URI newuri = new URI(art.getImageUrl());
//                // System.out.println(newuri);
//                ImageIcon imageIcon;
//                if (newuri.isAbsolute()) {
//                    imageIcon = new ImageIcon(newuri.toURL());
//                } else {
//                    imageIcon = new ImageIcon(art.getImageUrl());
//                } // load the image to a imageIcon
//                Image image = imageIcon.getImage(); // transform it
//                Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//                imageIcon = new ImageIcon(newimg);  // transform it back
//                JLabel imagelabel = new JLabel(imageIcon);
//                panelPictures.add(imagelabel);
//            } catch (URISyntaxException | MalformedURLException ew) {
//                throw new RuntimeException(ew);
//            }
//        }
    }
}