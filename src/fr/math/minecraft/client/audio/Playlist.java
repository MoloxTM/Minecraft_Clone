package fr.math.minecraft.client.audio;

import org.joml.Math;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private final List<Sound> sounds;
    private boolean running;
    private int currentIndex;
    private boolean looping;

    public Playlist(boolean looping) {
        this.sounds = new ArrayList<>();
        this.currentIndex = 0;
        this.running = false;
        this.looping = looping;
    }

    public void start() {
        currentIndex = 0;
        Sound sound = sounds.get(currentIndex);
        sound.play();
        running = true;
    }

    public void next() {
        if (currentIndex + 1 >= sounds.size()) {
            this.stop();
            return;
        }
        currentIndex = Math.clamp(0, sounds.size() - 1, currentIndex + 1);
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (currentIndex >= sounds.size()) {
            return;
        }
        Sound sound = sounds.get(currentIndex);
        sound.stop();
    }

    public void reset() {
        running = false;
        currentIndex = 0;
    }


    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    public Sound getPlayedSound() {
        if (currentIndex >= sounds.size()) {
            return looping ? sounds.get(0) : null;
        }
        return sounds.get(currentIndex);
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
}
