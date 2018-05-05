package com.example.android.popularmovies.moviesDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Trailers  implements Serializable {
    private final String ID="id";
    private final String ISO_639_1="iso_639_1";
    private final String ISO_3166_1="iso_3166_1";
    private final String KEY="key";
    private final String NAME="name";
    private final String SITE="site";
    private final String SIZE="size";
    private final String TYPE="type";
    private String idValue;
    private String iso6931Value;
    private String iso31661Value;
    private String keyValue;
    private String nameValue;
    private String siteValue;
    private int sizeValue;
    private String typeValue;

    public Trailers(JSONObject inputJson)
    {

        try {
            idValue=inputJson.getString(ID);
            iso6931Value=inputJson.getString(ISO_639_1);
            iso31661Value=inputJson.getString(ISO_3166_1);
            keyValue=inputJson.getString(KEY);
            nameValue=inputJson.getString(NAME);
            siteValue=inputJson.getString(SITE);
            sizeValue=inputJson.getInt(SIZE);
            typeValue=inputJson.getString(TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getIdValue() {
        return idValue;
    }

    public String getIso6931Value() {
        return iso6931Value;
    }

    public String getIso31661Value() {
        return iso31661Value;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public String getNameValue() {
        return nameValue;
    }

    public String getSiteValue() {
        return siteValue;
    }

    public int getSizeValue() {
        return sizeValue;
    }

    public String getTypeValue() {
        return typeValue;
    }







}
