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




        //check if triples with community cards
        List<GameObject.Card> cards = go.getCommunityCards();





        if(cards.size() != 0) {
            //check for full house
            int aces = 0;
            int queens = 0;
            int kings = 0;
            int boys = 0;
            int tens = 0;
            int nines = 0;
            int eights = 0;
            int sevens = 0;
            int sixes =0;
            int fives = 0;
            int fours= 0;
            int threes = 0;
            int twos = 0;

            /*for (GameObject.Card card : cards) {
                switch (card.getSuit()){
                    case "A": aces++;
                        break;
                    case "Q": queens++;break;
                    case "J": boys++;break;
                    case "K": kings++;break;
                    case "10": tens++;break;
                    case "9": nines++;break;
                    case "8": eights++;break;
                    case "7": sevens++;break;
                    case "6": sixes++;break;
                    case "5": fives++;break;
                    case "4": fours++;break;
                    case "3": threes++;break;
                    case "2": twos++;break;


                }

            }*/



            int count = 0;
            //check for flush
            if(sameSuit) {
                int flushCount = 0;
                for (GameObject.Card card : cards) {
                    if(card.getSuit().equalsIgnoreCase(firstCard.getSuit())) {
                        flushCount++;
                    }
                }
                if(flushCount >= 3) {
                    System.out.println("FLUSH!!!!");
                    return Integer.MAX_VALUE;
                }

            }

            for (GameObject.Card card : cards) {

                if(isTriple(firstCard, scndCard, card)){
                    return  call();
                }
                if(firstCard.getRank().equalsIgnoreCase(card.getRank())) {
                    count++;
                }
                if(scndCard.getRank().equalsIgnoreCase(card.getRank())) {
                    count++;
                }

            }

            if(count >=2) {
                //we have two pairs together with the community cards
                System.out.println("we have two pairs");
                return call() + 100;
            }

        }

        //check if two pairs with community cards

        //if two cards of same rank
        if((sameRank || sameSuit) && go.getRound() == 0) {

            return Integer.MAX_VALUE; // all in
        }

        //two high cards then we call
        if(twoHighCards(firstCard, scndCard)) {
            return call();
        }

        //int bet = call() + (rand.nextInt((max - min) + 1) + min);
        return call();
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
