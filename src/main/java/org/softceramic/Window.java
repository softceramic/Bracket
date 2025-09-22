package org.softceramic;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;

import java.util.concurrent.Callable;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public final long handle;
    private final Callable<Void> resizeMethod;
    public int width,height;


    public Window(String title,  WindowOptions options, Callable<Void> resizeMethod) {
        this.resizeMethod = resizeMethod;
        if (!glfwInit()) throw new IllegalStateException("[Window: Unable to initialize GLFW]");


        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);


        if (options.compatibleProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }


        if (options.width > 0 && options.height > 0) {
            this.width = options.width;
            this.height = options.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            this.width = vidmode.width();
            this.height = vidmode.height();
        }


        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) throw new RuntimeException("[Window: Failed to create window]");


        glfwSetFramebufferSizeCallback(handle, (window, w, h) -> resized(w, h));
        glfwSetErrorCallback((int errorcode, long msgptr) ->
                Logger.error("[Window: Error code [{}], msg [{}]", errorcode, MemoryUtil.memUTF8(msgptr))
        );
        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            keyCallBack(key, action);
        });


        glfwMakeContextCurrent(handle);
        if (options.framesPerSecond > 0) glfwSwapInterval(0);
        else glfwSwapInterval(1);
        glfwShowWindow(handle);


        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];
        glfwGetFramebufferSize(handle, arrWidth, arrHeight);
        width = arrWidth[0];
        height = arrHeight[0];


    }

    public void cleanup() {
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) callback.free();
    }

    public void keyCallBack(int key, int action) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(handle, true);
    }

    public boolean isKeyPressed(int keycode) {
        return glfwGetKey(handle, keycode) == GLFW_PRESS;
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    private void resized(int w, int h) {
        this.width = w;
        this.height = h;
        try {
            resizeMethod.call();

        } catch (Exception exception) {
            Logger.error("Error Calling Resize Callback", exception);
        }
    }

    public void update() {
        glfwSwapBuffers(handle);
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public static class WindowOptions {
        public int height, width;
        public int framesPerSecond;
        public int updatesPerSecond = Engine.TARGET_UPDATES_PER_SECOND;
        public boolean compatibleProfile;
    }
}
