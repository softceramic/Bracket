package org.softceramic.engine.graph;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import org.lwjgl.system.MemoryStack;

public class Texture {

    private int textureid;
    private String texturepath;

    public Texture(int width, int height, ByteBuffer bytebuffer) {
        this.texturepath = "";
        generateTexture(width, height, bytebuffer);
    }

    public Texture(String texturepath) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            this.texturepath = texturepath;

            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer buffer = stbi_load(texturepath, w, h, channels, 4);
            if (buffer == null) {
                throw new RuntimeException("Texture.java: image file " + texturepath + " failed to load");
            }

            int width = w.get(0);
            int height = h.get(0);
            generateTexture(width, height, buffer);
            stbi_image_free(buffer);

        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureid);
    }

    public void cleanup() {
        glDeleteTextures(textureid);
    }

    private void generateTexture(int width, int height, ByteBuffer bytebuffer) {

        textureid = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureid);
        glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, bytebuffer);
        glGenerateMipmap(GL_TEXTURE_2D);

    }

    public String getTexturepath() {
        return texturepath;
    }

}
