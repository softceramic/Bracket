package org.softceramic;

public class SETSYSTEM {

    public static SET init(SET set, int maxValue, int capacity) {
        set.sparse = new int[maxValue + 1];
        set.dense = new int[capacity];
        set.maxValue = maxValue;
        set.capacity = capacity;
        set.n = 0;

        return set;
    }

    public static void insert(ENTITY entity, SET set) {
        int id = entity.id;
        // out of range OR set is full OR element already there
        if (entity.id > set.maxValue || set.n >= set.capacity || search(entity, set) != -1) return;

        set.dense[set.n] = id; // append to dense
        set.sparse[id] = set.n; // position occupied by new element in dense is now an index in sparse
        set.n++; // increment n, grow dense by 1
    }


    // remember, sparse contains INDEXES, dense contains DATA.
    // so set.sparse[x] is an INDEX to x, not x itself.
    public static int search(ENTITY entity, SET set) {
        int id = entity.id;
        if (id > set.maxValue) return -1;
        if (set.sparse[id] < set.n && set.dense[set.sparse[id]] == id) return set.sparse[id];
        return -1;

    }

    public static void delete(ENTITY entity, SET set){
        int id = entity.id;
        int index = search(entity, set);
        if(index == -1) return; //element doesnt exist noting to delete

        int t = set.dense[set.n - 1]; // last element in dense
        set.dense[id] = t; // set that last element to the space occupied by what we deleting
        set.sparse[t] = index; // idk what this does
        set.n--;
    }
}
/**
 * @TODO figure out what line 43 does
 */