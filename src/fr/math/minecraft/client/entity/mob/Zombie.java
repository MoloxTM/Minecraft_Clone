package fr.math.minecraft.client.entity.mob;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.animations.*;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.entity.player.PlayerHand;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.client.events.listeners.EventListener;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.Sprite;
import fr.math.minecraft.shared.inventory.*;
import fr.math.minecraft.shared.network.GameMode;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.shared.world.*;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Zombie extends Mob {

    public Zombie(String name) {
        this.position = new Vector3f(0.0f, 100.0f, 0.0f);
        this.lastPosition = new Vector3f(0, 0, 0);
        this.gravity = new Vector3f(0, -0.0025f, 0);
        this.velocity = new Vector3f();
        this.inputs = new ArrayList<>();
        this.inventory = new PlayerInventory();
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 1.0f, 0.25f));
        this.animations = new ArrayList<>();
        this.lastUpdate = new EntityUpdate(new Vector3f(position), yaw, pitch, bodyYaw);
        this.aimedBlocks = new ArrayList<>();
        this.eventListeners = new ArrayList<>();
        this.sprite = new Sprite();
        this.miningAnimation = new MiningAnimation();
        this.action = PlayerAction.MINING;
        this.yaw = 0.0f;
        this.lastYaw = 0.0f;
        this.bodyYaw = 0.0f;
        this.pitch = 0.0f;
        this.lastMouseX = 0.0f;
        this.lastMouseY = 0.0f;
        this.speed = GameConfiguration.DEFAULT_SPEED;
        this.maxSpeed = 0.03f;
        this.maxFall = 0.03f;
        this.health = 20.0f;
        this.maxHealth = 20.0f;
        this.name = name;
        this.uuid = null;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.droppingItem = false;
        this.debugKeyPressed = false;
        this.occlusionKeyPressed = false;
        this.interpolationKeyPressed = false;
        this.inventoryKeyPressed = false;
        this.canHoldItem = false;
        this.canPlaceHoldedItem = false;
        this.movingMouse = true;
        this.sneaking = false;
        this.sprinting = false;
        this.flying = false;
        this.canJump = false;
        this.canBreakBlock = true;
        this.canPlaceBlock = true;
        this.jumping = false;
        this.placingBlock = false;
        this.breakingBlock = false;
        this.skin = null;
        this.skinPath = "res/textures/zombie.png";
        this.attackRay = new Ray(GameConfiguration.ATTACK_REACH);
        this.buildRay = new Ray(GameConfiguration.BUILDING_REACH * 3);
        this.breakRay = new Ray(GameConfiguration.BUILDING_REACH * 3);
        this.aimedPlacedBlocks = new ArrayList<>();
        this.lastInventory = inventory;
        this.initAnimations();
    }

    private void initAnimations() {
        animations.add(new ZombieWalkAnimation(this));
    }

    public void updatePosition(World world) {

        Vector3f front = new Vector3f();
        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f acceleration = new Vector3f(0, 0, 0);

        velocity.add(gravity);

        if (movingForward) {
            acceleration.add(front);
        }

        if (movingBackward) {
            acceleration.sub(front);
        }

        if (movingLeft) {
            acceleration.sub(right);
        }

        if (movingRight) {
            acceleration.add(right);
        }

        if (flying) {
            acceleration.add(new Vector3f(0.0f, .5f, 0.0f));
        }

        if (sneaking) {
            acceleration.sub(new Vector3f(0.0f, .5f, 0.0f));
        }

        if (jumping) {
            // handleJump();
            if (canJump) {
                maxFall = 0.5f;
                acceleration.y += 10.0f;
                canJump = false;
            }
        }

        velocity.add(acceleration.mul(speed));

        if (new Vector3f(velocity.x, 0, velocity.z).length() > maxSpeed) {
            Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
            velocityNorm.normalize().mul(maxSpeed);
            velocity.x = velocityNorm.x;
            velocity.z = velocityNorm.z;
        }

        if (new Vector3f(0, velocity.y, 0).length() > maxFall) {
            Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
            velocityNorm.normalize().mul(maxFall);
            velocity.y = velocityNorm.y;
        }

        position.x += velocity.x;
        handleCollisions(world, new Vector3f(velocity.x, 0, 0));

        position.z += velocity.z;
        handleCollisions(world, new Vector3f(0, 0, velocity.z));

        position.y += velocity.y;
        handleCollisions(world, new Vector3f(0, velocity.y, 0));

        velocity.mul(0.95f);

        PlayerInputData inputData = new PlayerInputData(movingLeft, movingRight, movingForward, movingBackward, flying, sneaking, jumping, yaw, pitch, sprinting, placingBlock, breakingBlock, droppingItem);
        inputs.add(inputData);
    }

}
