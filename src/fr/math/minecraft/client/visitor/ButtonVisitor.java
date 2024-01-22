package fr.math.minecraft.client.visitor;

import fr.math.minecraft.client.gui.buttons.PlayButton;

public interface ButtonVisitor<T> {

    T visit(PlayButton button);

}
