package fr.math.minecraft.server;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.util.Map;

public class TimeoutHandler extends Thread {

    private final MinecraftServer server;
    private final static int TIMEOUT_DELAY_MS = 10 * 1000;

    private final String uuid;
    private final Logger logger;

    public TimeoutHandler(MinecraftServer server, String uuid) {
        this.server = server;
        this.uuid = uuid;
        this.logger = LoggerUtility.getServerLogger(TimeoutHandler.class, LogType.TXT);
    }

    @Override
    public void run() {
        boolean timeout = false;
        while (!timeout) {
            long currentTime = System.currentTimeMillis();
            synchronized (server.getLastActivities()) {
                long lastTimeSeen = server.getLastActivities().get(uuid);
                if (currentTime - lastTimeSeen > TIMEOUT_DELAY_MS) {
                    synchronized (server.getClients()) {
                        Client client = server.getClients().get(uuid);
                        timeout = true;

                        logger.info("La connexion avec le client " + uuid + " (" + client.getName() + ") a été perdu... (timeout)");
                        server.getClients().remove(uuid);
                        server.getLastActivities().remove(uuid);
                    }
                }
            }
        }
    }
}
