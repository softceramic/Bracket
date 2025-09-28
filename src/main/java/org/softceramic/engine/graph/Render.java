package org.softceramic.engine.graph;

import org.lwjgl.opengl.GL;
import org.softceramic.engine.scene.Scene;
import org.softceramic.Window;

import static org.lwjgl.opengl.GL11.*;

public class Render {
    private final SceneRender sceneRender;

    public Render() {
        GL.createCapabilities();
        sceneRender = new SceneRender();
    }

    public void cleanup() {
        sceneRender.cleanUp();
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0,0, window.width, window.height);
        sceneRender.render(scene);
    }
}
