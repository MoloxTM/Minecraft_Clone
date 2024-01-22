package fr.math.minecraft.client.manager;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.gui.menus.Menu;

public class MenuManager {

    private final Game game;

    public MenuManager(Game game) {
        this.game = game;
    }

    public void registerMenu(Menu menu) {
        game.getMenus().put(menu.getClass(), menu);
    }

    public void unregisterMenu(Menu menu) {
        game.getMenus().remove(menu.getClass());
    }

    public void open(Class<? extends Menu> menuClass) {
        Menu menu = game.getMenus().get(menuClass);
        menu.open();
    }

    public void close(Class<? extends Menu> menuClass) {
        Menu menu = game.getMenus().get(menuClass);
        menu.close();
    }

}
