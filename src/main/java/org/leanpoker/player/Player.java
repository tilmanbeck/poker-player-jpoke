package org.leanpoker.player;

import com.google.gson.*;
import org.leanpoker.strategy.SimpleStrategy;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

public class Player {

    static Random rand = new Random();
    static int min = 0;
    static int max = 500;
    static final String VERSION = "Non-Default Java Killer-Bot";

    public static int betRequest(JsonElement request) {
        System.out.println("Initial request: \n" + request);
        GameObject go = new GameObject(request);
        JsonObject json = request.getAsJsonObject();



        JsonArray players = json.getAsJsonArray("players");

        JsonObject ourPlayer = (JsonObject) players.get(go.getIn_action());
        int playerBet = ourPlayer.get("bet").getAsInt();

        SimpleStrategy strategy = new SimpleStrategy(go);
        //int bet = strategy.performBet(playerBet);


        if(go.getPlayers().size() == 1) {
            //System.out.println("player size: " + 1);
            return Integer.MAX_VALUE; // all in if just us and another one
        }
        int bet = go.getCurrent_buy_in() - playerBet + go.getMinimumRaise() + (rand.nextInt((max - min) + 1) + min);
        System.out.println("Our bet: " + bet);
        return bet;
    }

    public static void showdown(JsonElement game) {
    }

    public static JsonElement getRanking(JsonElement cards) {
        String cardsstr;
        cardsstr = cards.toString();
        String urlString;
        try {
            urlString = "http://rainman.leanpoker.org/rank?cards=" + URLEncoder.encode(cardsstr);
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();
            String input = out.toString();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonElement json = gson.toJsonTree(input);
            return json;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
}
