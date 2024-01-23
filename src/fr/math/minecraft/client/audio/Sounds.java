package fr.math.minecraft.client.audio;

public enum Sounds {

    SUBWOOFER_LULLABY("res/sounds/music/subwoofer_lullaby.ogg"),
    KEY("res/sounds/music/key.ogg"),
    MINECRAFT("res/sounds/music/minecraft.ogg"),
    SWEDEN("res/sounds/music/sweden.ogg"),
    DANNY("res/sounds/music/danny.ogg"),
    FLOATING_TREES("res/sounds/music/floating_trees.ogg"),
    A_FAMILIAR_ROOM("res/sounds/music/a_familiar_room.ogg"),
    LEFT_TO_BLOOM("res/sounds/music/left_to_bloom.ogg"),
    TASWELL("res/sounds/music/taswell.ogg"),
    CLICK("res/sounds/click.ogg");

    private final String filePath;

    Sounds(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
