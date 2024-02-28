package fr.math.minecraft.client;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL33.*;

public class Shader {

    private final int id;

    private String readFile(String path) {
        StringBuilder lines = new StringBuilder();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.append(line).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines.toString();
    }

    public Shader(String shaderFilePath, String fragmentFilePath) {
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        String shaderSourceCode = readFile(shaderFilePath);
        String fragmentSourceCode = readFile(fragmentFilePath);

        glShaderSource(vertexShader, shaderSourceCode);
        glCompileShader(vertexShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException("Erreur lors de la compilation du vertex shader : " + shaderFilePath + glGetShaderInfoLog(vertexShader));
        }

        glShaderSource(fragmentShader, fragmentSourceCode);
        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException("Erreur lors de la compilation du vertex shader : " + fragmentFilePath + glGetShaderInfoLog(vertexShader));
        }

        id = glCreateProgram();
        glAttachShader(id, vertexShader);
        glAttachShader(id, fragmentShader);

        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) != GL_TRUE) {
            throw new RuntimeException("Erreur lors du lien du programme de shader : " + glGetProgramInfoLog(id));
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public int getId() {
        return id;
    }

    public void enable() {
        glUseProgram(id);
    }

    public void sendInt(String name, int value) {
        glUniform1i(glGetUniformLocation(id, name), value);
    }

    public void sendFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(id, name), value);
    }

    public void sendMatrix(String name, Matrix4f mat, FloatBuffer buffer) {
        mat.get(buffer);
        glUniformMatrix4fv(glGetUniformLocation(id, name), false, buffer);
    }

    public void sendVector3f(String name, Vector3f vector3f) {
        glUniform3f(glGetUniformLocation(id, name), vector3f.x, vector3f.y, vector3f.z);
    }

    public void sendVector2f(String name, Vector2f vector) {
        glUniform2f(glGetUniformLocation(id, name), vector.x, vector.y);
    }

}
