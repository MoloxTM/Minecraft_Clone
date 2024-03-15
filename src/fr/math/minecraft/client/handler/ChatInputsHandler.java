package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.network.payload.ChatPayload;
import org.lwjgl.glfw.GLFWCharCallback;

import static org.lwjgl.glfw.GLFW.*;

public class ChatInputsHandler {

    public void handleInputs(long window, ChatPayload chatWindow) {
        glfwSetCharCallback(window, new InputHandler(chatWindow));
    }

    private static class InputHandler extends GLFWCharCallback {

        private final ChatPayload chatPayload;

        public InputHandler(ChatPayload chatWindow) {
            this.chatPayload = chatWindow;
        }

        @Override
        public void invoke(long window, int codepoint) {
            char letter = (char) codepoint;
            chatPayload.getMessage().append(letter);
        }
    }

}
