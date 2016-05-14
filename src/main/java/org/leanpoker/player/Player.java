package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Random;

public class Player {

    static Random rand = new Random();
    static int min = 0;
    static int max = 100;
    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        GameObject go = new GameObject(request);
        JsonObject json = request.getAsJsonObject();

        JsonArray players = json.getAsJsonArray("players");

        JsonObject ourPlayer = (JsonObject) players.get(go.getIn_action());
        int playerBet = ourPlayer.get("bet").getAsInt();


        int bet = go.getCurrent_buy_in() - playerBet + go.getMinimumRaise() + (rand.nextInt((max - min) + 1) + min);
        return bet;
    }

    public static void showdown(JsonElement game) {
    }
}
