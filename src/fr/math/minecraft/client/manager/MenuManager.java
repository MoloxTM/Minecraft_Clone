package fr.math.minecraft.client.manager;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.gui.menus.Menu;

public class MenuManager {

    private final Game game;
    private Menu openedMenu;

    public MenuManager(Game game) {
        this.game = game;
        this.openedMenu = null;
    }

    public void registerMenu(Menu menu) {
        game.getMenus().put(menu.getClass(), menu);
    }

    public void unregisterMenu(Menu menu) {
        game.getMenus().remove(menu.getClass());
    }

    public void open(Class<? extends Menu> menuClass) {
        Menu menu = game.getMenus().get(menuClass);
        this.closeAllMenus();
        openedMenu = menu;
        menu.open();
    }

    public void closeAllMenus() {
        for (Menu menu : game.getMenus().values()) {
            menu.close();
        }
    }

    public void close(Class<? extends Menu> menuClass) {
        Menu menu = game.getMenus().get(menuClass);
        menu.close();
    }

    public Menu getOpenedMenu() {
        return openedMenu;
    }
}
