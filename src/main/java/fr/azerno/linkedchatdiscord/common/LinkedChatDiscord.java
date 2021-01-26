package fr.azerno.linkedchatdiscord.common;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;

import net.minecraftforge.fml.common.SidedProxy;

import net.minecraftforge.fml.common.event.*;

import org.apache.logging.log4j.Logger;

@Mod(
        modid = LinkedChatDiscord.MODID,
        name = LinkedChatDiscord.NAME,
        version = LinkedChatDiscord.VERSION,
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)
public class LinkedChatDiscord {

    @Instance
    public static LinkedChatDiscord instance;

    public static final String MODID = "linkedchatdiscord";
    public static final String NAME = "Linked Chat Discord";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @SidedProxy(serverSide = "fr.azerno.linkedchatdiscord.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        MinecraftForge.EVENT_BUS.register(CommonProxy.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRender();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {}



}
