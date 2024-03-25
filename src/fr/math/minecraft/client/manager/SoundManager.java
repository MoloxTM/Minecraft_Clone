package fr.math.minecraft.client.manager;


import fr.math.minecraft.client.audio.Playlist;
import fr.math.minecraft.client.audio.Sound;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.world.Material;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

public class SoundManager {

    private final Map<String, Sound> sounds;
    private final Playlist musicsPlaylist;
    private final Playlist grassFootStep;
    private final static Logger logger = LoggerUtility.getClientLogger(SoundManager.class, LogType.TXT);
    private final List<Sounds> musics;
    private static SoundManager instance = null;

    private SoundManager() {
        this.sounds = new HashMap<>();
        this.musics = new ArrayList<>();
        this.musicsPlaylist = new Playlist(true);
        this.grassFootStep = new Playlist(false);
        this.musics.add(Sounds.KEY);
        this.musics.add(Sounds.SWEDEN);
        this.musics.add(Sounds.SUBWOOFER_LULLABY);
        this.musics.add(Sounds.DANNY);
        this.musics.add(Sounds.A_FAMILIAR_ROOM);
        this.musics.add(Sounds.FLOATING_TREES);
        this.musics.add(Sounds.LEFT_TO_BLOOM);
        this.musics.add(Sounds.OXYGENE);
        this.musics.add(Sounds.LIVING_MICE);
        this.musics.add(Sounds.CLARK);
        this.musics.add(Sounds.WET_HANDS);
        this.musics.add(Sounds.TASWELL);
        this.musics.add(Sounds.MINECRAFT);
        this.musics.add(Sounds.MICE_ON_VENUS);
        this.musics.add(Sounds.HAGGSTROM);
        this.musics.add(Sounds.DRY_HANDS);
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
            logger.error("Impossible d'ajouter le son " + filePath + ", il est déjà enregistré ou le chemin spécifié n'existe pas.");
            return;
        }
        Sound sound = new Sound(filePath, loops);
        sounds.put(file.getAbsolutePath(), sound);
    }

    public Sound getRandomMusic() {
        Random random = new Random();
        int index = random.nextInt(musics.size());
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

    public Playlist getMusicsPlaylist() {
        return musicsPlaylist;
    }

    public Sounds getDigSound(Material material) {
        if (material == null) return Sounds.DIG_SNOW;
        switch (material) {
            case SNOW:
                return Sounds.DIG_SNOW;
            case OAK_LOG:
            case SPRUCE_WOOD:
            case BIRCH_LOG:
                return Sounds.DIG_WOOD;
        }
        return Sounds.DIG_SNOW;
    }

    public List<Sounds> getMusics() {
        return musics;
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
}
