package fr.math.minecraft.client.gui.menus;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.client.gui.buttons.BlockButton;
import fr.math.minecraft.client.gui.GuiText;
import fr.math.minecraft.client.manager.FontManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    protected GuiText title;
    protected List<BlockButton> buttons;
    protected List<GuiText> texts;
    protected boolean open;
    protected final Game game;

    public Menu(Game game, String title) {
        this(game);
        FontManager fontManager = new FontManager();
        float titleWidth = fontManager.getTextWidth(game.getRenderer().getFontMesh(), GameConfiguration.MENU_TITLE_SCALE, title);
        float titleHeight = fontManager.getTextHeight(game.getRenderer().getFontMesh(), GameConfiguration.MENU_TITLE_SCALE, title);
        this.title = new GuiText(
            title,
            GameConfiguration.WINDOW_CENTER_X - titleWidth / 2.0f,
            GameConfiguration.WINDOW_CENTER_Y - titleHeight / 2.0f + 100,
            0xFFFFFF
        );
        this.title.setScale(GameConfiguration.MENU_TITLE_SCALE);
    }

    public Menu(Game game) {
        this.game = game;
        this.buttons = new ArrayList<>();
        this.texts = new ArrayList<>();
        this.open = false;
        this.title = null;
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

    public GuiText getTitle() {
        return title;
    }
}
