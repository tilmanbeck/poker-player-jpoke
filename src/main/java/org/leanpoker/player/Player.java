package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.leanpoker.strategy.SimpleStrategy;

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



        //int bet = go.getCurrent_buy_in() - playerBet + go.getMinimumRaise() + (rand.nextInt((max - min) + 1) + min);
        int bet = strategy.performBet();
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
