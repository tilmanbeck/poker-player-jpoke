package org.leanpoker.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.leanpoker.player.Player;
import org.junit.Test;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerTest {

    @Test
    public void testBetRequest() throws Exception {

        Player pl = new Player ();
        Object obj = new JsonParser().parse(new FileReader("src/test/test.json"));
        JsonObject jsobj = (JsonObject) obj;
        JsonElement jsonEm = jsobj;

        assertNotNull("Null was returned", Player.betRequest(jsonEm));

    }
}
