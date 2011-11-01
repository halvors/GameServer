package org.halvors.Game.Server;

import java.util.HashMap;
import java.util.Map;

public enum WorldType {
    NORMAL(0);

    private final int id;
    private static final Map<Integer, WorldType> lookup = new HashMap<Integer, WorldType>();

    private WorldType(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public static WorldType getWorldType(int id) {
        return lookup.get(id);
    }

    static {
        for (WorldType w : values()) {
            lookup.put(w.getId(), w);
        }
    }
}