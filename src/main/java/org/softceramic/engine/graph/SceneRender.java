package org.softceramic.engine.graph;

import org.softceramic.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SceneRender {

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
    }

    public void cleanUp() {
        shaderProgram.cleanUp();
    }

    public void render(Scene scene) {
        shaderProgram.bind();
        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoID());
            glDrawArrays(GL_TRIANGLES, 0, mesh.getNumberOfVertices());
        });

        glBindVertexArray(0);
        shaderProgram.unbind();
    }


}
