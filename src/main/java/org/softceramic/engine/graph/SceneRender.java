package org.softceramic.engine.graph;

import org.softceramic.engine.scene.Entity;
import org.softceramic.engine.scene.Scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SceneRender {

    private UniformsMap uniformsmap;
    private final ShaderProgram shaderProgram;

    public SceneRender() {
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();

        shaderModuleDataList.add(
                new ShaderProgram.ShaderModuleData("src/main/java/org/softceramic/resources/shaders/scene.vert", GL_VERTEX_SHADER)
        );

        shaderModuleDataList.add(
                new ShaderProgram.ShaderModuleData("src/main/java/org/softceramic/resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
        );

        shaderProgram = new ShaderProgram(shaderModuleDataList);
        shaderProgram.validate();
        createUniforms();
    }

    private void createUniforms() {
        uniformsmap = new UniformsMap(shaderProgram.getProgramID());
        uniformsmap.createUniform("projectionmatrix");
        uniformsmap.createUniform("modelmatrix");
    }

    public void cleanUp() {
        shaderProgram.cleanUp();
    }

    public void render(Scene scene) {
        shaderProgram.bind();
        uniformsmap.setUniform("projectionmatrix", scene.getProjection().getProjectionMatrix());
        Collection<Model> models = scene.getModelMap().values();

        for (Model model : models) {
            model.getMeshList().stream().forEach(mesh -> {
                glBindVertexArray(mesh.getVaoID());
                List<Entity> entities = model.getEntitiesList();
                for (Entity entity : entities) {
                    uniformsmap.setUniform("modelmatrix", entity.getModelmatrix());
                    glDrawElements(GL_TRIANGLES, mesh.getNumberOfVertices(), GL_UNSIGNED_INT, 0);
                }
            });
        }
        glBindVertexArray(0);
        shaderProgram.unbind();
    }

}
