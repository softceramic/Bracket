package org.softceramic.engine.graph;

import org.lwjgl.opengl.GL30;
import org.softceramic.engine.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class ShaderProgram {

    private final int programID;

    public ShaderProgram(List<ShaderModuleData> shaderModuleDataList) {
        programID = glCreateProgram();
        if (programID == 0) {
            throw new RuntimeException("{ShaderProgram: Could not create shader}");
        }

        List<Integer> shaderModules = new ArrayList<>();
        shaderModuleDataList.forEach(s -> shaderModules.add(
                createShader(Utils.readFile(s.shaderFile), s.shaderType)
        ));

        link(shaderModules);

    }

    public void bind() {
        glUseProgram(programID);
    }

    public void cleanUp() {
        unbind();
        if (programID != 0) {
            glDeleteProgram(programID);
        }
    }

    protected int createShader(String shaderCode, int shaderType) {
        int shaderID = glCreateShader(shaderType);
        if (shaderID == 0) {
            throw new RuntimeException("{ShaderProgram: Error creating shader of type " + shaderType);
        }

        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);

        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("{ShaderProgram: Error compiling shader code " + glGetShaderInfoLog(shaderID, 1024));
        }

        glAttachShader(programID, shaderID);
        return shaderID;
    }

    public int getProgramID() {
        return programID;
    }

    private void link(List<Integer> shaderModules) {
        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("{ShaderProgram: Error linking shader code " + glGetProgramInfoLog(programID, 1024));
        }

        shaderModules.forEach(s -> glDetachShader(programID, s));
        shaderModules.forEach(GL30::glDeleteShader);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void validate() {
        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException("{ShaderProgram: error validating shader code " + glGetProgramInfoLog(programID, 1024));
        }
    }

    public record ShaderModuleData(String shaderFile, int shaderType) {

    }
}
