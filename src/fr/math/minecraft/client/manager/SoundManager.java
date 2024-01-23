package fr.math.minecraft.client.manager;


import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.audio.Sound;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

public class SoundManager {

    private final Map<String, Sound> sounds;
    private final static Logger logger = LoggerUtility.getClientLogger(SoundManager.class, LogType.TXT);

    private final ArrayList<Sounds> musics;

    public SoundManager() {
        this.sounds = new HashMap<>();
        this.musics = new ArrayList<>();

        this.musics.add(Sounds.KEY);
        this.musics.add(Sounds.SWEDEN);
        this.musics.add(Sounds.SUBWOOFER_LULLABY);
        this.musics.add(Sounds.DANNY);
        this.musics.add(Sounds.A_FAMILIAR_ROOM);
        this.musics.add(Sounds.FLOATING_TREES);
        this.musics.add(Sounds.LEFT_TO_BLOOM);
    }

    public Map<String, Sound> getSounds() {
        return sounds;
    }

    public Collection<Sound> getAllSounds() {
        return sounds.values();
    }

    public Sound getSound(Sounds sound) {
        return this.getSound(sound.getFilePath());
    }

    public Sound getSound(String soundFile) {
        File file = new File(soundFile);
        return sounds.get(file.getAbsolutePath());
    }

    public void addSound(Sounds sound, boolean loops) {
        this.addSound(sound.getFilePath(), loops);
    }

    public void addSound(String filePath, boolean loops) {
        File file = new File(filePath);
        if (sounds.containsKey(file.getAbsolutePath()) || !file.exists()) {
            logger.warn("Impossible d'ajouter le son " + filePath + ", il est déjà enregistré ou le chemin spécifié n'existe pas.");
            return;
        }
        Sound sound = new Sound(filePath, loops);
        sounds.put(file.getAbsolutePath(), sound);
    }

    public Sound getRandomMusic() {
        Random random = new Random();
        int index = random.nextInt(0, musics.size());
        Sounds sound = musics.get(index);
        return this.getSound(sound);
    }

    public void play(Sounds soundName) {
        Sound sound = this.getSound(soundName);
        if (sound == null) {
            logger.warn("Impossible de lire le son " + soundName + " ce son est inconnu de la liste.");
            return;
        }
        sound.play();
    }

    public void stop(Sounds soundName) {
        Sound sound = this.getSound(soundName);
        if (sound == null) {
            logger.warn("Impossible de stopper le son " + soundName + " ce son est inconnu de la liste.");
            return;
        }
        sound.stop();
    }

}
