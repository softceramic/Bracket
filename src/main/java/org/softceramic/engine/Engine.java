package org.softceramic.engine;

import org.softceramic.AppLogic;
import org.softceramic.Window;
import org.softceramic.engine.graph.Render;
import org.softceramic.engine.scene.Scene;

public class Engine {
    public static final int TARGET_UPDATES_PER_SECOND = 30;
    private final AppLogic applogic;
    private final Window window;
    private final Render render;
    private boolean running;
    private final Scene scene;
    private final int targetFramesPerSecond;
    private final int targetUpdatesPerSecond;

    public Engine(String windowtitle, Window.WindowOptions options, AppLogic applogic) {

        this.window = new Window(windowtitle, options, () -> {
            resize();
            return null;
        });

        targetFramesPerSecond = options.framesPerSecond;
        targetUpdatesPerSecond = options.updatesPerSecond;
        this.applogic = applogic;
        render = new Render();
        scene = new Scene();
        applogic.init(window, scene, render);
        running = true;
    }

    private void cleanup() {
        applogic.cleanup();
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }

    private void resize() {

    }

    private void run() {
        long initialTime = System.currentTimeMillis();
        float timeElapsedBetweenUpdates = 1000.0f / targetUpdatesPerSecond;
        float timeElapsedBetweenRenderCalls = targetFramesPerSecond > 0 ? (1000.0f / targetFramesPerSecond) : 0;
        float deltaUpdate = 0;
        float deltaFramesPerSecond = 0;
        long updateTime = initialTime;

        while (running && !window.windowShouldClose()) {
            window.pollEvents();

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timeElapsedBetweenUpdates;
            deltaFramesPerSecond += (now - initialTime) / timeElapsedBetweenRenderCalls;
            if (targetFramesPerSecond <= 0 || deltaFramesPerSecond >= 1)
                applogic.input(window, scene, now - initialTime);

            if (deltaUpdate >= 1) {
                long deltaTimeMilliseconds = now - updateTime;
                applogic.update(window, scene, deltaTimeMilliseconds);
                updateTime = now;
                deltaUpdate--;
            }

            if (targetFramesPerSecond <= 0 || deltaFramesPerSecond >= 1) {
                render.render(window, scene);
                deltaFramesPerSecond--;
                window.update();
            }

            initialTime = now;
        }
        cleanup();
    }


}
