package fr.math.minecraft.shared;

import fr.math.minecraft.client.MinecraftClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PacketCompressor {

    public static byte[] compress(String jsonData) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bos);
        gzipOutputStream.write(jsonData.getBytes(StandardCharsets.UTF_8));
        gzipOutputStream.close();

        return bos.toByteArray();
    }

    public static String decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
        GZIPInputStream gzipInputStream = new GZIPInputStream(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[MinecraftClient.MAX_RESPONSE_LENGTH];
        int size;
        while ((size = gzipInputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, size);
        }
        gzipInputStream.close();
        bos.close();
        bis.close();
        return bos.toString(StandardCharsets.UTF_8).trim();
    }

}
