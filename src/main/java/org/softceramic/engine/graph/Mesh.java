package org.softceramic.engine.graph;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final List<Integer> vboIDList;
    private final int vaoID;
    private final int numberOfVertices;

    public Mesh(float[] positions, float[] colors, int[] indices) {
        this.numberOfVertices = indices.length;
        vboIDList = new ArrayList<>();
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        positionsVboSetup(positions);
        colourVboSetup(colors);
        indexVboSetup(indices);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    private void positionsVboSetup(float[] positions) {
        int vboID = glGenBuffers();
        vboIDList.add(vboID);
        FloatBuffer positionsBuffer = MemoryUtil.memCallocFloat(positions.length);
        positionsBuffer.put(0, positions);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        MemoryUtil.memFree(positionsBuffer);

    }

    private void colourVboSetup(float[] colours) {
        int vboID = glGenBuffers();
        vboIDList.add(vboID);
        FloatBuffer coloursbuffer = MemoryUtil.memCallocFloat(colours.length);
        coloursbuffer.put(0, colours);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, coloursbuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        MemoryUtil.memFree(coloursbuffer);

    }

    private void indexVboSetup(int[] indices) {
        int vboID = glGenBuffers();
        vboIDList.add(vboID);
        IntBuffer indicesbuffer = MemoryUtil.memCallocInt(indices.length);
        indicesbuffer.put(0, indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesbuffer, GL_STATIC_DRAW);

        MemoryUtil.memFree(indicesbuffer);
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
