package fr.math.minecraft.client.gui.buttons;

import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.client.meshs.ButtonMesh;
import fr.math.minecraft.client.visitor.ButtonVisitor;

public class BackToTitleButton extends BlockButton {

    public BackToTitleButton() {
        super(
            "Retour à l'écran titre",
            GameConfiguration.WINDOW_CENTER_X - ButtonMesh.BUTTON_WIDTH / 2.0f,
            GameConfiguration.WINDOW_CENTER_Y - ButtonMesh.BUTTON_HEIGHT / 2.0f - 50,
            -9
        );
    }

    @Override
    public <T> T accept(ButtonVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
