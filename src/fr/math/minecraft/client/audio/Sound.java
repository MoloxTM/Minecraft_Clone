package fr.math.minecraft.client.audio;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Sound {

    private int bufferId;
    private int sourceId;
    private final String filePath;
    private boolean playing, loops;
    private final static Logger logger = LoggerUtility.getClientLogger(Sound.class, LogType.TXT);
    private final static float DEFAULT_VOLUME = 0.2f;

    public Sound(String filePath, boolean loops) {
        this.filePath = filePath;
        this.playing = false;
        this.loops = loops;
    }

    public void load() {
        MemoryStack.stackPush();
        IntBuffer channelsBuffer = MemoryStack.stackMallocInt(1);
        MemoryStack.stackPush();
        IntBuffer sampleRateBuffer = MemoryStack.stackMallocInt(1);

        ShortBuffer rawAudioBuffer = STBVorbis.stb_vorbis_decode_filename(filePath, channelsBuffer, sampleRateBuffer);

        if (rawAudioBuffer == null) {
            logger.error("Impossible de charger le fichier son " + filePath);
            MemoryStack.stackPop();
            MemoryStack.stackPop();
            return;
        }

        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();

        MemoryStack.stackPop();
        MemoryStack.stackPop();

        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        this.bufferId = alGenBuffers();
        alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

        sourceId = alGenSources();
        alSourcei(sourceId, AL_BUFFER, bufferId);
        alSourcei(sourceId, AL_LOOPING, loops ? 1 : 0);
        alSourcei(sourceId, AL_POSITION, 0);
        alSourcef(sourceId, AL_GAIN, DEFAULT_VOLUME);

        free(rawAudioBuffer);

        logger.info("Son " + filePath + " chargée avec succès");
    }

    public int getState() {
        return alGetSourcei(sourceId, AL_SOURCE_STATE);
    }

    public void play() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);

        if (state == AL_STOPPED) {
            playing = false;
            alSourcei(sourceId, AL_POSITION, 0);
        }

        if (playing) {
            return;
        }

        alSourcePlay(sourceId);
        playing = true;
    }

    public void stop() {
        if (!playing) {
            return;
        }
        alSourceStop(sourceId);
        playing = false;
    }

    public void delete() {
        alDeleteSources(sourceId);
        alDeleteBuffers(bufferId);
    }

    public boolean isPlaying() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            playing = false;
        }
        return playing;
    }

    public String getFilePath() {
        return filePath;
    }
}
