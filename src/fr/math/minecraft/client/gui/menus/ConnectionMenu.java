package fr.math.minecraft.client.gui.menus;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.gui.buttons.BackToTitleButton;
import fr.math.minecraft.client.gui.buttons.BlockButton;

public class ConnectionMenu extends Menu {

    public ConnectionMenu(Game game) {
        super(game, "Connexion en cours...");
    }

    @Override
    public void loadContent() {

        BlockButton backToTitle = new BackToTitleButton();

        this.buttons.add(backToTitle);
    }

    @Override
    public MenuBackgroundType getBackgroundType() {
        return MenuBackgroundType.DIRT_BACKGROUND;
    }

    @Override
    public void update() {

    }
}
