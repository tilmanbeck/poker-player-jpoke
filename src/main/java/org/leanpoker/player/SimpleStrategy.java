package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.Random;

public class SimpleStrategy {

    static Random rand = new Random();
    static int min = 0;
    static int max = 100;
    GameObject go;
    public SimpleStrategy(GameObject go) {
        this.go = go;
    }

    public int performBet(int playerBet) {



        GameObject.Card firstCard = go.getOurPlayer().getHoleCards().get(0);
        GameObject.Card scndCard = go.getOurPlayer().getHoleCards().get(1);
        boolean sameRank = firstCard.getRank().equalsIgnoreCase(scndCard.getRank());
        boolean sameSuit = firstCard.getSuit().equalsIgnoreCase(scndCard.getSuit());

        //if two cards of same rank
        if((sameRank || sameSuit) && go.getRound() == 0) {

            return Integer.MAX_VALUE; // all in
        }


        int bet = go.getCurrent_buy_in() - playerBet + go.getMinimumRaise() + (rand.nextInt((max - min) + 1) + min);
        return bet;
    }
}
