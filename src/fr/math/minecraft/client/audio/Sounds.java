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
    CLARK("res/sounds/music/clark.ogg"),
    DRY_HANDS("res/sounds/music/dry_hands.ogg"),
    HAGGSTROM("res/sounds/music/haggstrom.ogg"),
    LIVING_MICE("res/sounds/music/living_mice.ogg"),
    MICE_ON_VENUS("res/sounds/music/mice_on_venus.ogg"),
    OXYGENE("res/sounds/music/oxygene.ogg"),
    WET_HANDS("res/sounds/music/wet_hands.ogg"),
    CLICK("res/sounds/click.ogg"),
    POP("res/sounds/pop.ogg");

    private final String filePath;

    Sounds(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
