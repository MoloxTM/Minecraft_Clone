package test;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector3f;
import org.junit.Test;

public class TestRayCasting {

    @Test
    public void testRay() {
        Ray ray = new Ray(GameConfiguration.BUILDING_REACH);

        Camera camera = new Camera(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
        Player player = new Player("");
        player.setPosition(new Vector3f(0, 20, 0));

        World world = new World();
        Chunk chunk = new Chunk(0, 0, 0);
        for (int i = 0; i < Chunk.VOLUME; i++) {
            chunk.getBlocks()[i] = Material.STONE.getId();
        }

        player.setPitch(-90.0f);
        camera.update(player);
        camera.calculateFront(camera.getFront());
        System.out.println("Front" + camera.getFront());
        ray.update(player.getPosition(), camera.getFront(), world, false);

        System.out.println(ray.getAimedBlock());
    }

}
