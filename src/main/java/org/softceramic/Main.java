package org.softceramic;

import org.softceramic.engine.Engine;
import org.softceramic.engine.graph.Mesh;
import org.softceramic.engine.graph.Render;
import org.softceramic.engine.scene.Scene;

public class Main implements AppLogic {
    public static void main(String[] args) {
        Main main = new Main();
        Window.WindowOptions opts = new Window.WindowOptions();
        opts.height = 600;
        opts.width = 800;

        Engine engine = new Engine("Bracket", opts, main);
        engine.start();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        float[] positions = new float[]{
                -0.5f, 0.5f, -1.05f,
                -0.5f, -0.5f, -1.05f,
                0.5f, -0.5f, -1.05f,
                0.5f, 0.5f, -1.05f,
        };

        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,

        };

        int[] indices = new int[]{
                0, 1, 3,
                3, 1, 2
        };
        Mesh mesh = new Mesh(positions, colours, indices);
        scene.addMesh("triangle", mesh);
    }


    @Override
    public void input(Window window, Scene scene, long deltaTimeMilliseconds) {

    }

    @Override
    public void update(Window window, Scene scene, long deltaTimeMilliseconds) {

    }
}
