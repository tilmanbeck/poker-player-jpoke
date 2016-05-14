package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    String tournamentId;
    String game_id;
    int minimumRaise;
    int current_buy_in;
    int round;
    int bet_index;
    int pot;
    int dealer;
    int orbits;
    int in_action;
    List<CommunityCard> communityCards = new ArrayList<CommunityCard>();
    List<Player> players = new ArrayList<Player>();
    OurPlayer ourPlayer;

    public String getGame_id() {
        return game_id;
    }

    public int getMinimumRaise() {
        return minimumRaise;
    }

    public int getCurrent_buy_in() {
        return current_buy_in;
    }

    public int getRound() {
        return round;
    }

    public int getBet_index() {
        return bet_index;
    }

    public int getPot() {
        return pot;
    }

    public int getDealer() {
        return dealer;
    }

    public int getOrbits() {
        return orbits;
    }

    public int getIn_action() {
        return in_action;
    }

    public String getTournamentId() {

        return tournamentId;
    }

    public GameObject(JsonElement request) {
        JsonObject json = request.getAsJsonObject();

        tournamentId = json.get("tournament_id").getAsString();
        game_id  = json.get("game_id").getAsString();
        minimumRaise = json.get("minimum_raise").getAsInt();
        current_buy_in= json.get("current_buy_in").getAsInt();
        round =  json.get("round").getAsInt();
        bet_index = json.get("bet_index").getAsInt();
        pot = json.get("pot").getAsInt();
        dealer = json.get("dealer").getAsInt();
        orbits = json.get("orbits").getAsInt();
        in_action= json.get("in_action").getAsInt();

        JsonArray players = json.getAsJsonArray("players");
        JsonArray community_cards = json.getAsJsonArray("community_cards");


        JsonObject oP = (JsonObject) players.get(in_action);
        ourPlayer = new OurPlayer();
    }

    private class Player {

    }

    private class OurPlayer {

    }

    private class CommunityCard {

    }



}
