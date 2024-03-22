package fr.math.minecraft.client.audio;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.manager.SoundManager;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.glfw.GLFW.*;

public class PlaylistPlayer extends Thread {

    private final static Logger logger = LoggerUtility.getClientLogger(PlaylistPlayer.class, LogType.TXT);
    private final Playlist playlist;

    public PlaylistPlayer(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public void run() {
        Game game = Game.getInstance();
        playlist.start();
        while (playlist.isRunning()) {

            Sound sound = playlist.getPlayedSound();

            if (sound == null) {
                playlist.stop();
                break;
            }

            logger.info("Currently playing " + sound.getFilePath());
            sound.play();

            while (sound.getState() == AL_PLAYING) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("Passage au son suivant.");
            playlist.next();

        }
    }
}
