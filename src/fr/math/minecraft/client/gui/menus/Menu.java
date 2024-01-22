package fr.math.minecraft.client.gui.menus;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.gui.buttons.BlockButton;
import fr.math.minecraft.client.gui.GuiText;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    protected List<BlockButton> buttons;
    protected List<GuiText> texts;
    protected boolean open;
    protected final Game game;

    public Menu(Game game) {
        this.game = game;
        this.buttons = new ArrayList<>();
        this.texts = new ArrayList<>();
        this.open = false;
        this.loadContent();
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public List<BlockButton> getButtons() {
        return buttons;
    }

    public abstract void loadContent();
    public abstract MenuBackgroundType getBackgroundType();

    public abstract void update();

    public List<GuiText> getTexts() {
        return texts;
    }
}
