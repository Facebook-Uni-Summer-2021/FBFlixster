package com.example.fbflixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Video {
    String id;
    String key;
    String name;
    String site;
    String type;

    //Constructor to take JSON Object and return Movie object
    public Video (JSONObject jsonObject) throws JSONException {
        //For JSONs, ensure to type LETTER FOR LETTER for correct keys
        id = jsonObject.getString("id");
        key = jsonObject.getString("key");
        name = jsonObject.getString("name");
        site = jsonObject.getString("site");
        type = jsonObject.getString("type");
    }

    //Constructs movie for each JSON array (movie API indicates a
    // movie is a single array in the JSON)
    public static List<Video> fromJsonArray (JSONArray jsonVideoArray)
            throws JSONException {
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < jsonVideoArray.length(); i++) {
            videos.add(new Video(jsonVideoArray.getJSONObject(i)));
        }
        return videos;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
