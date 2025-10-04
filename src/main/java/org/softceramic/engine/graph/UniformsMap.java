package org.softceramic.engine.graph;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class UniformsMap {
    private final Map<String, Integer> uniforms;
    private final int programID;

    public UniformsMap(int programID) {
        this.programID = programID;
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformname) {
        int uniformlocation = glGetUniformLocation(programID, uniformname);

        if (uniformlocation < 0) {
            throw new RuntimeException("{UniformsMap.java: Could not find uniform " +
                    uniformname +
                    " in shader program " +
                    programID);
        }

        uniforms.put(uniformname, uniformlocation);
    }

    public void setUniform(String uniformname, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            Integer location = uniforms.get(uniformname);

            if (location == null) {
                throw new RuntimeException("{UniformsMap.java: could not find uniform " + uniformname);
            }

            glUniformMatrix4fv(location.intValue(), false, value.get(stack.mallocFloat(16)));
        }

    }
}
