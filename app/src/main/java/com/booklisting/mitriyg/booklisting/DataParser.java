package com.booklisting.mitriyg.booklisting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class DataParser {

    public JSONArray getJsonArray(String jsonString, String items) throws JSONException {
        System.out.println(jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONArray(items);
    }


    public ArrayList<Book> parseBooks(JSONArray jsonArray) throws JSONException {
        ArrayList<Book> items = new ArrayList<>();
        int lengthJsonArr = jsonArray.length();
        for (int i = 0; i < lengthJsonArr; i++) {
            Book item = new Book();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.has("volumeInfo")) {
                JSONObject volumeInfo = jsonObject.getJSONObject("volumeInfo");
                if (volumeInfo.has("title")) {
                    item.setTitle(volumeInfo.getString("title"));
                }
                if (volumeInfo.has("authors")) {
                    ArrayList<String> authors = parseAuthors(volumeInfo.getJSONArray("authors"));
                    item.setAuthors(authors);
                }
            }
            items.add(item);
        }
        return items;
    }

    private ArrayList<String> parseAuthors(JSONArray jsonArray) throws JSONException {
        ArrayList<String> authors = new ArrayList<String>();
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                authors.add(jsonArray.get(i).toString());
            }
        }
        return authors;
    }

    public String formatStr(String str) {
        return str.replace("[", "").replace("]", "").trim();
    }
}
