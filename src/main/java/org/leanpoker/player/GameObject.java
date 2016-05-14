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

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public OurPlayer getOurPlayer() {
        return ourPlayer;
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
                JsonArray json_cards = obj.getAsJsonArray("hole_cards");
                ArrayList<Card> cards = new ArrayList<Card>();

                for(int j = 0 ; j < json_cards.size() ; j++) {
                    JsonObject json_card = (JsonObject) json_cards.get(j);
                    Card card = new Card(json_card.get("rank").getAsString(), json_card.get("suit").getAsString());
                    cards.add(card);
                }

                ourPlayer = new OurPlayer(obj.get("id").getAsInt(), obj.get("name").getAsString(),
                        obj.get("status").getAsString(),
                        obj.get("stack").getAsInt(),
                        obj.get("bet").getAsInt(), cards);
            } else {
                Player pl = new Player(obj.get("id").getAsInt(), obj.get("name").getAsString(),
                        obj.get("status").getAsString(),
                        obj.get("stack").getAsInt(),
                        obj.get("bet").getAsInt(), new ArrayList<Card>());
                players.add(pl);
            }


        }

    }

    public class Player {
        int id;
        String name;
        String status;
        int stack;
        int bet;
        //attention, this exist in other players than others in the end when cards are revealed!
        List<Card> holeCards = new ArrayList<Card>();

        public Player(int id, String name, String status, int stack, int bet, List<Card> holeCards) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.stack = stack;
            this.bet = bet;
            this.holeCards = holeCards;
        }


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public int getStack() {
            return stack;
        }

        public int getBet() {
            return bet;
        }

        public List<Card> getHoleCards() {
            return holeCards;
        }

    }

    public class OurPlayer extends Player {
        public OurPlayer(int id, String name, String status, int stack, int bet, List<Card> holeCards) {
            super(id, name, status, stack, bet, holeCards);
        }
    }

    public class Card {

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
