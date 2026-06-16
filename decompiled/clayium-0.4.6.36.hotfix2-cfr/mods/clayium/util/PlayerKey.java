/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.util;

import java.util.HashMap;
import java.util.Map;

public class PlayerKey {
    private HashMap<Integer, Integer> mouse = new HashMap();
    private HashMap<KeyType, Integer> keys = new HashMap();

    private Map.Entry<Integer, Integer> getMouseEntry(int input) {
        for (Map.Entry<Integer, Integer> entry : this.mouse.entrySet()) {
            if (entry.getKey() != input) continue;
            return entry;
        }
        return null;
    }

    private Map.Entry<KeyType, Integer> getKeyEntry(KeyType key) {
        for (Map.Entry<KeyType, Integer> entry : this.keys.entrySet()) {
            if (entry.getKey() != key) continue;
            return entry;
        }
        return null;
    }

    public int getMouseLength(int input) {
        Map.Entry<Integer, Integer> entry = this.getMouseEntry(input);
        return entry == null ? Integer.MIN_VALUE : entry.getValue();
    }

    public void setMouseInput(int input, boolean event) {
        Map.Entry<Integer, Integer> entry = this.getMouseEntry(input);
        if (entry == null & event) {
            this.mouse.put(input, 0);
        }
        if (entry != null & !event) {
            this.mouse.remove(entry.getKey());
        }
    }

    public int getKeyLength(KeyType key) {
        Map.Entry<KeyType, Integer> entry = this.getKeyEntry(key);
        return entry == null ? Integer.MIN_VALUE : entry.getValue();
    }

    public void setKeyInput(KeyType key, boolean event) {
        Map.Entry<KeyType, Integer> entry = this.getKeyEntry(key);
        if (entry == null & event) {
            this.keys.put(key, 0);
        }
        if (entry != null & !event) {
            this.keys.remove((Object)entry.getKey());
        }
    }

    public void update() {
        for (Map.Entry<Integer, Integer> entry : this.mouse.entrySet()) {
            entry.setValue(entry.getValue() + 1);
        }
        for (Map.Entry<Object, Integer> entry : this.keys.entrySet()) {
            entry.setValue(entry.getValue() + 1);
        }
    }

    public static enum KeyType {
        SPRINT(0),
        USE_ITEM(1);

        private int id = -1;

        private KeyType(int id) {
            this.id = id;
        }

        public static KeyType getKeyFromId(int id) {
            for (KeyType key : KeyType.values()) {
                if (key.id != id) continue;
                return key;
            }
            return null;
        }

        public int getId() {
            return this.id;
        }
    }
}

