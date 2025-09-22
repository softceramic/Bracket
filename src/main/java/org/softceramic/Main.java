package org.softceramic;

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

    }

    @Override
    public void input(Window window, Scene scene, long deltaTimeMilliseconds) {

    }

    @Override
    public void update(Window window, Scene scene, long deltaTimeMilliseconds) {

    }
}
