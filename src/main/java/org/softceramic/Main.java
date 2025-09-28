package org.softceramic;

import org.softceramic.engine.Engine;
import org.softceramic.engine.graph.Mesh;
import org.softceramic.engine.graph.Render;
import org.softceramic.engine.scene.Scene;

public class Main implements AppLogic {
    public static void main(String[] args) {
        Main main = new Main();
        Engine engine = new Engine("Bracket", new Window.WindowOptions(), main);
        engine.start();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        float[] positions = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        Mesh mesh = new Mesh(positions, 3);
        scene.addMesh("triangle", mesh);
    }


    @Override
    public void input(Window window, Scene scene, long deltaTimeMilliseconds) {

    }

    @Override
    public void update(Window window, Scene scene, long deltaTimeMilliseconds) {

    }
}
