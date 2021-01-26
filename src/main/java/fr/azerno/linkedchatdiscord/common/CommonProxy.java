package fr.azerno.linkedchatdiscord.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class CommonProxy {

    public void registerRender() {
        LinkedChatDiscord.logger.info("method server side");
    }

    @SubscribeEvent
    public static void grabServeChatEvent(ServerChatEvent event)
    {
        String data = event.getComponent().getFormattedText();

        sendTextToDiscord(data);
    }

    @SubscribeEvent
    public static void grabAdvancementEvent(AdvancementEvent event)
    {
        DisplayInfo displayInfo = event.getAdvancement().getDisplay();
        if(displayInfo == null || !displayInfo.shouldAnnounceToChat()) return;

        String playerName = event.getEntityPlayer().getName();
        String text = (new TextComponentTranslation("chat.type.advancement." + displayInfo.getFrame().getName())).getFormattedText();

        String displayText = event.getAdvancement().getDisplayText().getFormattedText();

        String data = playerName + text + displayText;

        sendTextToDiscord(data);
    }

    @SubscribeEvent
    public static void grabPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        String playerName = event.player.getName();
        String text = (new TextComponentTranslation("multiplayer.player.joined")).getFormattedText();

        String data = playerName + text;

        sendTextToDiscord(data);
    }

    @SubscribeEvent
    public static void grabPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        String playerName = event.player.getName();
        String text = (new TextComponentTranslation("multiplayer.player.left")).getFormattedText();

        String data = (playerName + text);

        sendTextToDiscord(data);
    }

    private static void sendTextToDiscord(String textData)
    {
        textData = ChatFormatting.stripFormatting(textData);

        InputStream stream = CommonProxy.class.getResourceAsStream("/config.json");
        JsonObject obj =  JsonUtils.fromJson(new Gson(), new InputStreamReader(stream), JsonObject.class);

        String key = Objects.requireNonNull(obj).get("key").getAsString();

        try {
            URL url = new URL("http://azerno.fr:8080/linkedChatDiscord/?key=" + key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setDoOutput(true);
            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            request.writeBytes(textData);

            request.flush();
            request.close();

            connection.connect();
            LinkedChatDiscord.logger.debug(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}