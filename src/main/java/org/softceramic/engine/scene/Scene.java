package org.softceramic.engine.scene;

import org.softceramic.engine.graph.Model;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    private final Map<String, Model> modelmap;
    private final Projection projection;

    public Scene(int width, int height) {
        modelmap = new HashMap<>();
        projection = new Projection(width, height);
    }

    public Projection getProjection() {
        return projection;
    }

    public void resize(int width, int height) {
        projection.updateProjectionMatrix(width, height);
    }

    public void addEntity(Entity entity) {
        String modelid = entity.getModelid();
        Model model = modelmap.get(modelid);
        if (model == null) {
            throw new RuntimeException("scene.java: could not find model " + modelid);
        }
        model.getEntitiesList().add(entity);
    }

    public void addModel(Model model) {
        modelmap.put(model.getId(), model);
    }

    public void cleanup() {
        modelmap.values().forEach(Model::cleanup);
    }

    public Map<String, Model> getModelMap() {
        return modelmap;
    }
}
