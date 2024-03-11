package fr.math.minecraft.client.builder;

import fr.math.minecraft.client.vertex.ItemVertex;
import fr.math.minecraft.shared.world.Material;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ItemMeshBuilder {

    private static final String ITEM_ATLAS_PATH = "res/textures/gui/gui_blocks.png";
    private final static int ITEM_PIXEL_SIZE = 16;
    private final static float ITEM_SIZE = 1.0f / ITEM_PIXEL_SIZE;

    private static void buildTopSquare(ArrayList<ItemVertex> vertices, Material material, float vertexX, float vertexY, float step, int x, int y, int textureWidth, int textureHeight) {

        float textureX = material.getBlockIconX() * ITEM_PIXEL_SIZE + x;
        float textureY = material.getBlockIconY() * ITEM_PIXEL_SIZE + ITEM_PIXEL_SIZE - 1 - y;

        Vector2f[] textureCoords = new Vector2f[] {
            new Vector2f(textureX / textureWidth, textureY / textureHeight),
            new Vector2f(textureX / textureWidth, (textureY + 1) / textureHeight),
            new Vector2f((textureX + 1) / textureWidth, (textureY + 1) / textureHeight),
            new Vector2f((textureX + 1) / textureWidth, textureY / textureHeight)
        };

        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[0]));
        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f - step), textureCoords[1]));
        vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f - step), textureCoords[2]));
        vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f - step), textureCoords[2]));
        vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f), textureCoords[3]));
        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[0]));
    }

    private static void buildLeftSquare(ArrayList<ItemVertex> vertices, Material material, float vertexX, float vertexY, float step, int x, int y, int textureWidth, int textureHeight) {

        float textureX = material.getBlockIconX() * ITEM_PIXEL_SIZE + x;
        float textureY = material.getBlockIconY() * ITEM_PIXEL_SIZE + ITEM_PIXEL_SIZE - 1 - y;

        Vector2f[] textureCoords = new Vector2f[] {
            new Vector2f(textureX / textureWidth, textureY / textureHeight),
            new Vector2f(textureX / textureWidth, (textureY + 1) / textureHeight),
            new Vector2f((textureX + 1) / textureWidth, (textureY + 1) / textureHeight),
            new Vector2f((textureX + 1) / textureWidth, textureY / textureHeight)
        };

        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));
        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f - step), textureCoords[1]));
        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[2]));
        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[2]));
        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f), textureCoords[3]));
        vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));
    }

        public static ItemVertex[] buildItemMesh(Material material, ArrayList<Integer> indices) {
        ArrayList<ItemVertex> vertices = new ArrayList<>();

        int currentIndex = 0;

        try {
            BufferedImage itemAtlas = ImageIO.read(new File(ITEM_ATLAS_PATH));
            BufferedImage itemImage = itemAtlas.getSubimage(material.getBlockIconX() * ITEM_PIXEL_SIZE, itemAtlas.getHeight() - ITEM_PIXEL_SIZE - material.getBlockIconY() * ITEM_PIXEL_SIZE, ITEM_PIXEL_SIZE, ITEM_PIXEL_SIZE);

            float vertexX = ITEM_SIZE;
            float vertexY = ITEM_SIZE;
            float step = ITEM_SIZE * 2;

            for (int x = 0; x < ITEM_PIXEL_SIZE; x++) {
                for (int y = ITEM_PIXEL_SIZE - 1; y >= 0; y--) {

                    int pixel = itemImage.getRGB(x, y);
                    int alpha = (pixel >> 24) & 0xFF;

                    if (y != ITEM_PIXEL_SIZE - 1) vertexY += step;

                    if (alpha == 0.0f) {

                        if (y + 1 < ITEM_PIXEL_SIZE) {
                            int nextPixel = itemImage.getRGB(x, y + 1);
                            int nextAlpha = (nextPixel >> 24) & 0xFF;

                            if (nextAlpha != 0.0f) {
                                buildTopSquare(vertices, material, vertexX, vertexY - step, step, x, y + 1, itemAtlas.getWidth(), itemAtlas.getHeight());
                            }
                        }

                        if (y - 1 >= 0) {
                            int nextPixel = itemImage.getRGB(x, y - 1);
                            int nextAlpha = (nextPixel >> 24) & 0xFF;

                            if (nextAlpha != 0.0f) {
                                buildTopSquare(vertices, material, vertexX, vertexY + step, step, x, y - 1, itemAtlas.getWidth(), itemAtlas.getHeight());
                            }
                        }


                        if (x + 1 < ITEM_PIXEL_SIZE) {
                            int nextPixel = itemImage.getRGB(x + 1, y);
                            int nextAlpha = (nextPixel >> 24) & 0xFF;

                            if (nextAlpha != 0.0f) {
                                buildLeftSquare(vertices, material, vertexX + step, vertexY, step, x + 1, y, itemAtlas.getWidth(), itemAtlas.getHeight());
                            }
                        }

                        if (x - 1 >= 0) {
                            int nextPixel = itemImage.getRGB(x - 1, y);
                            int nextAlpha = (nextPixel >> 24) & 0xFF;

                            if (nextAlpha != 0.0f) {
                                buildLeftSquare(vertices, material, vertexX - step, vertexY, step, x - 1, y, itemAtlas.getWidth(), itemAtlas.getHeight());
                            }
                        }

                        continue;
                    }

                    float textureX = material.getBlockIconX() * ITEM_PIXEL_SIZE + x;
                    float textureY = material.getBlockIconY() * ITEM_PIXEL_SIZE + ITEM_PIXEL_SIZE - 1 - y;

                    Vector2f[] textureCoords = new Vector2f[] {
                        new Vector2f(textureX / itemAtlas.getWidth(), textureY / itemAtlas.getHeight()),
                        new Vector2f(textureX / itemAtlas.getWidth(), (textureY + 1) / itemAtlas.getHeight()),
                        new Vector2f((textureX + 1) / itemAtlas.getWidth(), (textureY + 1) / itemAtlas.getHeight()),
                        new Vector2f((textureX + 1) / itemAtlas.getWidth(), textureY / itemAtlas.getHeight()),
                    };

                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f), textureCoords[0]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[1]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY, -0.5f), textureCoords[3]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f), textureCoords[0]));

                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f - step), textureCoords[1]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f -step), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f - step), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY, -0.5f - step), textureCoords[3]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));

                    if (x == 0) {
                        buildLeftSquare(vertices, material, vertexX, vertexY, step, x, y, itemAtlas.getWidth(), itemAtlas.getHeight());
                    }

                    if (x == ITEM_PIXEL_SIZE - 1) {
                        buildLeftSquare(vertices, material, vertexX + step, vertexY, step, x, y, itemAtlas.getWidth(), itemAtlas.getHeight());
                    }

                    /*

                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[0]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f - step), textureCoords[1]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f - step), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f - step), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f), textureCoords[3]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[0]));

                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f - step), textureCoords[1]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY - step, -0.5f), textureCoords[3]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));

                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY + step, -0.5f - step), textureCoords[1]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f - step), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY + step, -0.5f - step), textureCoords[2]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX + step, vertexY, -0.5f - step), textureCoords[3]));
                    vertices.add(new ItemVertex(new Vector3f(vertexX, vertexY, -0.5f - step), textureCoords[0]));

                     */

                    indices.add(currentIndex);
                    indices.add(currentIndex + 1);
                    indices.add(currentIndex + 2);
                    indices.add(currentIndex + 3);

                    currentIndex += 4;
                }

                vertexX += step;
                vertexY = ITEM_SIZE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vertices.toArray(new ItemVertex[0]);

    }
}
