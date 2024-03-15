package fr.math.minecraft.shared.entity.mob;

import fr.math.minecraft.client.animations.*;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.Sprite;
import fr.math.minecraft.shared.inventory.*;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.shared.world.*;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Zombie extends Mob {

    public Zombie(String name) {
        super(name);
        this.skinPath = "res/textures/zombie.png";
        this.behavior = MobBehavior.HOSTILE;
        this.mobType = MobType.ZOMBIE;
        this.initAnimations();
    }

    private void initAnimations() {
        animations.add(new ZombieWalkAnimation(this));
    }

}
