package org.softceramic;

import org.softceramic.engine.graph.Render;
import org.softceramic.engine.scene.Scene;

public interface AppLogic {

    void cleanup();

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long deltaTimeMilliseconds);

    void update(Window window, Scene scene, long deltaTimeMilliseconds);
}
