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
        JsonObject json = request.getAsJsonObject();

        String tournamentId = json.get("tournament_id").getAsString();
        String game_id = json.get("game_id").getAsString();
        int minimumRaise = json.get("minimum_raise").getAsInt();
        int current_buy_in = json.get("current_buy_in").getAsInt();
        int round = json.get("round").getAsInt();
        int bet_index = json.get("bet_index").getAsInt();
        int pot = json.get("pot").getAsInt();
        int dealer = json.get("dealer").getAsInt();
        int orbits = json.get("orbits").getAsInt();
        int in_action = json.get("in_action").getAsInt();
        JsonObject players = json.getAsJsonObject("players");
        JsonObject community_cards = (JsonObject) json.get("community_cards");



        return rand.nextInt((max - min) + 1) + min;
    }

    public static void showdown(JsonElement game) {
    }
}
