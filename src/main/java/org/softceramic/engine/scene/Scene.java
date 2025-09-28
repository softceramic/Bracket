package org.softceramic.engine.scene;


import org.softceramic.engine.graph.Mesh;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    private final Map<String, Mesh> meshMap;

    public Scene() {
        meshMap = new HashMap<>();
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
