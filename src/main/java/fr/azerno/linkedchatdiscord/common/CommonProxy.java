package fr.azerno.linkedchatdiscord.common;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.ServerChatEvent;

import java.io.IOException;
import java.net.*;

public class CommonProxy {

    public void registerRender() {
        LinkedChatDiscord.logger.info("method server side");
    }

    @SubscribeEvent //grab chat message in game
    public static void grabMessage(ServerChatEvent event)
    {
        LinkedChatDiscord.logger.debug(event.getUsername() + " " + event.getMessage());
    }
}

/*
* TODO: - créer le repo git sur github du mod
*       - enregistrer et modifier le format du string
*       - envoyer le string au serveur en HTTP port 8080
*       - Traiter la requête avec le bot discord
*       - Envoyer le message sur le bon channel
*       - Récuperer les messages envoyés sur le channel discord
*       - Envoyer les messages sur le serveur minecraft
*       - Traiter les messages reçus de discord et les envoyer dans le tchat
*
* Futur Problème : - Rate limit de discord en cas de spam
* */
