package fr.math.minecraft.client.math;

import org.joml.Vector3f;

public class ViewBobbing {

    private float sinTime;
    private float effectSpeed;
    private float effectIntensityX;
    private float effectIntensity;
    private Vector3f position;
    private float y;

    public ViewBobbing() {
        this(0, 0.035f, 0.2f, 0.15f);
    }

    public ViewBobbing(float sinTime, float effectSpeed, float effectIntensityX, float effectIntensity) {
        this.sinTime = sinTime;
        this.effectSpeed = effectSpeed;
        this.effectIntensityX = effectIntensityX;
        this.effectIntensity = effectIntensity;
        this.y = 0;
        this.position = new Vector3f();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getSinTime() {
        return sinTime;
    }

    public void setSinTime(float sinTime) {
        this.sinTime = sinTime;
    }

    public float getEffectSpeed() {
        return effectSpeed;
    }

    public void setEffectSpeed(float effectSpeed) {
        this.effectSpeed = effectSpeed;
    }

    public float getEffectIntensityX() {
        return effectIntensityX;
    }

    public void setEffectIntensityX(float effectIntensityX) {
        this.effectIntensityX = effectIntensityX;
    }

    public float getEffectIntensity() {
        return effectIntensity;
    }

    public void setEffectIntensity(float effectIntensity) {
        this.effectIntensity = effectIntensity;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
