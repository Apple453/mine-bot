package net.applee.minecraft.client;

import net.applee.minecraft.protocol.NetworkHandler;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.packets.c2s.login.HandshakeC2SPacket;
import net.applee.minecraft.protocol.packet.packets.c2s.login.LoginHelloC2SPacket;
import net.applee.minecraft.protocol.packet.packets.c2s.login.QueryRequestC2SPPacket;
import net.applee.minecraft.translation.Translation;
import net.applee.minecraft.utils.Counter;
import net.applee.minecraft.utils.gameprofile.GameProfileManager;
import net.applee.minecraft.utils.logging.Logger;
import net.applee.minecraft.world.World;
import net.applee.minecraft.world.block.Blocks;

import java.io.IOException;
import java.util.Optional;

public class MinecraftClient {

    public static final int DEFAULT_PORT = 25565;

    public static final String brand = "vanilla";
    public static final int PROTOCOL_VERSION = 760;

    public static MinecraftClient getInstance() {
        return instance;
    }

    public final Counter tickCounter = new Counter();

    private static MinecraftClient instance;
    public Logger logger;
    public String nickname;
    public String host;
    public int port;
    public final ClientSettings settings;
    public final Input input;
    public final IEventHandler eventHandler;

    public boolean pinging = false;

    public ServerMetadata serverMetadata;

    public NetworkHandler networkHandler;
    public ClientPlayerEntity player;
    public World world;
    public GameProfileManager profileManager;

    public MinecraftClient(String nickname, String host, int port, IEventHandler eventHandler) {
        instance = this;

        try {
            this.logger = new Logger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("Initializing...");

        this.host = host;
        this.port = port;
        this.nickname = nickname;
        this.eventHandler = eventHandler;
        eventHandler.setClient(this);

        this.input = new Input();
        this.profileManager = new GameProfileManager();

        this.settings = new ClientSettings();
        this.serverMetadata = new ServerMetadata();

        Translation.init(settings.getLanguage());
        this.eventHandler.init();

        Blocks.init();

        logger.info("Init complete.");
        logger.info("Started with a name: " + nickname);

    }

    public void pingServer() {
        pinging = true;
        logger.info("Pinging %s...".formatted(host + (port == DEFAULT_PORT ? "" : ":" + port)));
        networkHandler = new NetworkHandler();
        networkHandler.connect(host, port);
        networkHandler.sendPacket(new HandshakeC2SPacket(PROTOCOL_VERSION, host, port, HandshakeC2SPacket.PingState.PING));
        networkHandler.sendPacket(new QueryRequestC2SPPacket());
    }

    public void joinToServer() {
        logger.info("Connecting to %s...".formatted(host + (port == DEFAULT_PORT ? "" : ":" + port)));
        networkHandler = new NetworkHandler();
        networkHandler.connect(host, port);
        networkHandler.sendPacket(new HandshakeC2SPacket(PROTOCOL_VERSION, host, port, HandshakeC2SPacket.PingState.PLAY));
        networkHandler.sendPacket(new LoginHelloC2SPacket(nickname, Optional.empty()));
    }



}

