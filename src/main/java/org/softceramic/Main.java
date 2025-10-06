package org.softceramic;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.softceramic.engine.Engine;
import org.softceramic.engine.graph.Mesh;
import org.softceramic.engine.graph.Model;
import org.softceramic.engine.graph.Render;
import org.softceramic.engine.scene.Entity;
import org.softceramic.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements AppLogic {

    private Entity cubeEntity;
    private Vector4f displInc = new Vector4f();
    private float rotation;

    public static void main(String[] args) {
        Main main = new Main();
        Window.WindowOptions opts = new Window.WindowOptions();
        opts.height = 300;
        opts.width = 300;

        Engine engine = new Engine("Bracket", opts, main);
        engine.start();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        float[] positions = new float[]{
            // v0
            -0.5f, 0.5f, 0.5f,
            //v1
            -0.5f, -0.5f, 0.5f,
            //v2
            0.5f, -0.5f, 0.5f,
            //v3
            0.5f, 0.5f, 0.5f,
            //v4
            -0.5f, 0.5f, -0.5f,
            //v5
            0.5f, 0.5f, -0.5f,
            //v6
            -0.5f, -0.5f, -0.5f,
            //v7
            0.5f, -0.5f, -0.5f
        };

        float[] colours = new float[]{
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.0f, 0.5f,};

        int[] indices = new int[]{
            //front face
            0, 1, 3, 3, 1, 2,
            // top face
            4, 0, 3, 5, 4, 3,
            //right
            3, 2, 7, 5, 3, 7,
            //left
            6, 1, 0, 6, 0, 4,
            //bottom
            2, 1, 6, 2, 6, 7,
            //back
            7, 6, 4, 7, 4, 5

        };
        List<Mesh> meshlist = new ArrayList<>();
        Mesh mesh = new Mesh(positions, colours, indices);
        meshlist.add(mesh);
        String modelcubeid = "cube-model";
        Model model = new Model(meshlist, modelcubeid);
        scene.addModel(model);

        cubeEntity = new Entity("cube-model", modelcubeid);
        cubeEntity.setPosition(0, 0, -2);
        scene.addEntity(cubeEntity);

    }

    @Override
    public void input(Window window, Scene scene, long deltaTimeMilliseconds) {
        displInc.zero();
        if (window.isKeyPressed(GLFW_KEY_W)) {
            displInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            displInc.y = 1;
        } else if (window.isKeyPressed(GLFW_KEY_A)) {
            displInc.x = 1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            displInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_UP)) {
            displInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            displInc.z = 1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            displInc.w = -1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            displInc.w = 1;
        }
        displInc.mul(deltaTimeMilliseconds / 1000.0f);
        Vector3f entityposition = cubeEntity.getPosition();
        cubeEntity.setPosition(displInc.x + entityposition.x, displInc.y + entityposition.y, displInc.z + entityposition.z);
        cubeEntity.setScale(cubeEntity.getScale() + displInc.w);
        cubeEntity.updateModelMatrix();
    }

    @Override
    public void update(Window window, Scene scene, long deltaTimeMilliseconds) {
        rotation += 1.0f;
        if (rotation > 360) {
            rotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
        cubeEntity.updateModelMatrix();
    }
}
