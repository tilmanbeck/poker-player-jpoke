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
    List<Card> communityCards = new ArrayList<Card>();
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

        JsonArray json_players = json.getAsJsonArray("players");
        JsonArray json_community_cards = json.getAsJsonArray("community_cards");


        //parsing community cards
        for (int i = 0 ; i < json_community_cards.size() ; i++) {
            JsonObject obj = (JsonObject) json_community_cards.get(i);
            Card c = new Card(obj.get("rank").getAsString(), obj.get("suit").getAsString());
            communityCards.add(c);
        }

        //parsing players
        for (int i = 0 ; i < json_players.size() ; i++) {
            JsonObject obj = (JsonObject) json_players.get(i);

            if(i == getIn_action()) {
                ourPlayer = new OurPlayer();
            } else {
                Player pl = new Player();
                players.add(pl);
            }


        }

    }

    private class Player {
        int id;
        String name;
        String status;
        int stack;
        int bet;

        //attention, this exist in other players than others in the end when cards are revealed!
        List<Card> holeCards = new ArrayList<Card>();
    }

    private class OurPlayer extends Player {

    }

    private class Card {

        String rank;
        String suit;

        private Card(String rank, String suit) {
            this.rank = rank;
            this.suit = suit;
        }

        public String getRank() {
            return rank;
        }

        public String getSuit() {
            return suit;
        }
    }



}
