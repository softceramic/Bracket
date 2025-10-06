package org.softceramic.engine.scene;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Entity {

    private final Matrix4f modelmatrix;
    private final Vector3f position;
    private final Quaternionf rotation;
    private final String id, modelid;
    private float scale;

    public Entity(String id, String modelid) {
        this.id = id;
        this.modelid = modelid;
        modelmatrix = new Matrix4f();
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = 1;
    }

    public Matrix4f getModelmatrix() {
        return modelmatrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public String getId() {
        return id;
    }

    public String getModelid() {
        return modelid;
    }

    public float getScale() {
        return scale;
    }

    public final void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setRotation(float x, float y, float z, float angle) {
        this.rotation.fromAxisAngleRad(x, y, z, angle);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void updateModelMatrix() {
        modelmatrix.translationRotateScale(position, rotation, scale);
    }

}
