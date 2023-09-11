package net.applee.minecraft.protocol;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.listening.ClientListener;
import net.applee.minecraft.protocol.packet.listening.ServerListener;
import net.applee.minecraft.utils.ThreadExecutor;
import net.applee.minecraft.utils.logging.Logger;
import net.applee.minecraft.utils.string.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkHandler extends Socket {

    public ServerListener serverListener;
    public ClientListener clientListener;

    public InputStream serverOutputStream;
    public OutputStream serverInputStream;

    public final IEventHandler eventHandler;

    private final MinecraftClient mc;
    public final Timer timer = new Timer();
    public Timer tickTimer;
    public boolean isTicking = false;

    public boolean isPlaying = false;
    public int packetCompressThreshold = -1;

    public NetworkHandler() {
        mc = MinecraftClient.getInstance();
        this.eventHandler = mc.eventHandler;
        serverListener = new ServerListener();
        clientListener = new ClientListener(this);
        serverListener.init(this, eventHandler);
    }

    public void connect(String host, int port) {
        mc.logger.info("Connecting.");
        try {
            InetSocketAddress address = new InetSocketAddress(host, port);
            connect(address);
            serverOutputStream = this.getInputStream();
            serverInputStream = this.getOutputStream();
            ThreadExecutor.runThread(serverListener::listen);

            mc.logger.info("Start client listener.");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    clientListener.listen();
                }
            }, 0, 10);
        } catch (IOException e) {
            mc.logger.exception(Logger.LogLevel.WARNING, e);
            eventHandler.onDisconnect(Text.of("Socket closed"));
            disconnect();
        }
    }

    public void runTickEvent() {
        if (!isTicking) {
            mc.logger.info("Start tick event.");
            tickTimer = new Timer();
            tickTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    eventHandler.onTick();
                    mc.tickCounter.increment();
                }
            }, 0, 50);
            isTicking = true;
        }
    }

    public void killTickEvent() {
        mc.logger.info("Stop tick event.");
        if (isTicking) {
            tickTimer.cancel();
            tickTimer.purge();
            isTicking = false;
        }
    }

    public void disconnect() {
        mc.logger.info("Disconnecting.");
        killSchedulers();
        serverListener.terminate();
        eventHandler.onTerminate();
        try {
            this.close();
        } catch (IOException e) {
            mc.logger.exception(e);
        }
    }

    private void killSchedulers() {
        mc.logger.info("Stop schedulers.");
        killTickEvent();
        timer.cancel();
        timer.purge();
    }

    public void sendPacket(C2SPacket packet) {
        if (eventHandler.onPacketSend(packet)) return;
        clientListener.addPacket(packet);
    }

    public void sendBytes(byte[] bytes) throws IOException {
        serverInputStream.write(bytes);
        serverInputStream.flush();
    }

}
