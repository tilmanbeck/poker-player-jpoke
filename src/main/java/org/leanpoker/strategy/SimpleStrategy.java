package org.leanpoker.strategy;

import com.google.gson.JsonElement;
import org.leanpoker.player.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleStrategy implements Strategy {

    static Random rand = new Random();
    static int min = 0;
    static int max = 100;
    GameObject go;
    public SimpleStrategy(GameObject go) {
        this.go = go;
    }

    @Override
    public int performBet() {



        GameObject.Card firstCard = go.getOurPlayer().getHoleCards().get(0);
        GameObject.Card scndCard = go.getOurPlayer().getHoleCards().get(1);
        boolean sameRank = firstCard.getRank().equalsIgnoreCase(scndCard.getRank());
        boolean sameSuit = firstCard.getSuit().equalsIgnoreCase(scndCard.getSuit());


        if(go.getPlayers().size() == 1) {
            return Integer.MAX_VALUE; // all in if just us and another one
        }

        List<GameObject.Card> cards = go.getCommunityCards();
        if(cards.size() != 0) {
            for (GameObject.Card card : cards) {
                if(isTriple(firstCard, scndCard, card)){
                    return  call();
                }
            }

        }

        //if two cards of same rank
        if((sameRank || sameSuit) && go.getRound() == 0) {

            return Integer.MAX_VALUE; // all in
        }


        if(twoHighCards(firstCard, scndCard)) {
            return call();
        }

        //TODO


        int bet = call() + (rand.nextInt((max - min) + 1) + min);
        return bet;
    }

    private boolean isTriple(GameObject.Card firstCard, GameObject.Card scndCard, GameObject.Card card) {
        //System.out.println("Ranks: " + firstCard.getRank() + scndCard.getRank() + card.getRank());
        if(firstCard.getRank().equalsIgnoreCase(scndCard.getRank())) {
            if (firstCard.getRank().equalsIgnoreCase(card.getRank())) {
                //System.out.println("3 equal cards");
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean twoHighCards(GameObject.Card firstCard, GameObject.Card scndCard) {

        return isHighCard(firstCard.getRank()) && isHighCard(scndCard.getRank());
    }


    public int call() {
        return go.getCurrent_buy_in() - go.getOurPlayer().getBet()+ go.getMinimumRaise();
    }

    //card is either A,J,Q,K
    private boolean isHighCard(String rank) {
        return (rank.equalsIgnoreCase("A") || rank.equalsIgnoreCase("J") || rank.equalsIgnoreCase("K") || rank.equalsIgnoreCase("Q"));
    }

}
