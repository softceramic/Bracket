package org.softceramic.engine.scene;


import org.softceramic.engine.graph.Mesh;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    private final Map<String, Mesh> meshMap;
    private final Projection projection;

    public Scene(int width, int height) {
        meshMap = new HashMap<>();
        projection = new Projection(width, height);
    }

    public Projection getProjection() {
        return projection;
    }

    public void resize(int width, int height) {
        projection.updateProjectionMatrix(width, height);
    }

    public void addMesh(String meshID, Mesh mesh) {
        meshMap.put(meshID, mesh);
    }

    public void cleanup() {
        meshMap.values().forEach(Mesh::cleanUp);
    }

    public Map<String, Mesh> getMeshMap() {
        return meshMap;
    }
}
