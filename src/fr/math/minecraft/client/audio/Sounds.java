package fr.math.minecraft.client.audio;

public enum Sounds {

    SUBWOOFER_LULLABY("res/sounds/music/subwoofer_lullaby.ogg"),
    KEY("res/sounds/music/key.ogg"),
    MINECRAFT("res/sounds/music/minecraft.ogg"),
    SWEDEN("res/sounds/music/sweden.ogg"),
    DANNY("res/sounds/music/danny.ogg"),
    CLICK("res/sounds/click.ogg");

    private final String filePath;

    Sounds(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
