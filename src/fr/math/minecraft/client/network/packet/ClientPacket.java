package fr.math.minecraft.client.network.packet;

public interface ClientPacket {

    public void send();
    public String toJSON();

}
