package fr.math.minecraft.client.packet;

public interface ClientPacket {

    public void send();
    public String toJSON();

}
