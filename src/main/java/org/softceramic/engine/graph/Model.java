package org.softceramic.engine.graph;

import org.softceramic.engine.scene.Entity;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private final String id;
    private final List<Entity> entitiesList;
    private final List<Mesh> meshList;

    public Model(List<Mesh> meshList, String id) {
        this.id = id;
        this.meshList = meshList;
        entitiesList = new ArrayList<>();
    }

    public void cleanup() {
        meshList.forEach(Mesh::cleanUp);
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public String getId() {
        return id;
    }

    public List<Mesh> getMeshList() {
        return meshList;
    }

}
