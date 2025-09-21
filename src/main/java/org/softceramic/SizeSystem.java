package org.softceramic;

public class SizeSystem {
    public static void init(SizeComponent sizeComponent, int maxValues, int capacity) {
        sizeComponent.entities = new SET();
        SETSYSTEM.init(sizeComponent.entities, maxValues, capacity);
        sizeComponent.widths = new float[capacity];
        sizeComponent.heights = new float[capacity];
    }

    public static void addComponent(SizeComponent sizeComponent, ENTITY entity, float width, float height) {
        SETSYSTEM.insert(entity, sizeComponent.entities);
        int index = sizeComponent.entities.sparse[entity.id];
        sizeComponent.widths[index] = width;
        sizeComponent.heights[index] = height;
    }

    public static void testGrowAllEntities(SizeComponent sizeComponent){
        for(int i = 0; i < sizeComponent.entities.n; i++){
            sizeComponent.widths[i] += 1.0f;
            sizeComponent.heights[i] += 1.0f;
        }
    }
}

