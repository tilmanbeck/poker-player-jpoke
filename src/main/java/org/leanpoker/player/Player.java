package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.Random;

public class Player {

    static Random rand = new Random();
    static int min = 0;
    static int max = 100;
    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {

        return rand.nextInt((max - min) + 1) + min;
    }

    public static void showdown(JsonElement game) {
    }
}
