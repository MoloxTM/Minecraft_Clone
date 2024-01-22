package fr.math.minecraft.client.visitor;

import fr.math.minecraft.client.gui.buttons.BackToTitleButton;
import fr.math.minecraft.client.gui.buttons.PlayButton;

public interface ButtonVisitor<T> {

    T visit(PlayButton button);
    T visit(BackToTitleButton button);

}
