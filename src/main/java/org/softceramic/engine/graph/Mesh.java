package org.softceramic.engine.graph;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final List<Integer> vboIDList;
    private final int vaoID;
    private final int numberOfVertices;

    public Mesh(float[] positions, int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        vboIDList = new ArrayList<>();
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // positions vbo
        int vboID = glGenBuffers();
        vboIDList.add(vboID);
        FloatBuffer positionsBuffer = MemoryUtil.memCallocFloat(positions.length);
        positionsBuffer.put(0, positions);

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        MemoryUtil.memFree(positionsBuffer);
    }

    public void cleanUp() {
        vboIDList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoID);
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public final int getVaoID() {
        return vaoID;
    }


}
