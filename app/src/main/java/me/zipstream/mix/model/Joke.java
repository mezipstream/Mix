package me.zipstream.mix.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.zipstream.mix.base.Constants;

public class Joke implements Serializable, Constants {

    private String mJokeContent;

    public static String getJokeUrl(int page) {
        return JOKE_URL_BASE + page;
    }

    public static List<Joke> parse(JSONArray contentArray) {
        List<Joke> jokes = new ArrayList<>();

        for (int i = 0; i < contentArray.length(); i++) {
            Joke joke = new Joke();
            JSONObject contentObject = contentArray.optJSONObject(i);

            joke.setJokeContent(contentObject.optString("text_content"));

            jokes.add(joke);
        }

        return jokes;
    }

    public String getJokeContent() {
        return mJokeContent;
    }

    public void setJokeContent(String jokeContent) {
        mJokeContent = jokeContent;
    }
}
