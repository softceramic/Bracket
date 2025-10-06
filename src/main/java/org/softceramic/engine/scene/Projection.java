package org.softceramic.engine.scene;

import org.joml.Matrix4f;

public class Projection {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_FAR = 1000.f;
    private static final float Z_NEAR = 0.01f;

    private final Matrix4f projectionmatrix;

    public Projection(int width, int height) {
        projectionmatrix = new Matrix4f();
        updateProjectionMatrix(width, height);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionmatrix;
    }

    public void updateProjectionMatrix(int width, int height) {
        projectionmatrix.setPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
    }
}
