/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.util.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mods.clayium.util.crafting.WeightedResult;

public class WeightedList<E>
extends ArrayList<WeightedResult<E>> {
    public boolean add(E result, int weight) {
        return this.add(new WeightedResult<E>(result, weight));
    }

    public List<E> getResultList() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < this.size(); ++i) {
            list.add(((WeightedResult)this.get((int)i)).result);
        }
        return list;
    }

    public int getWeightSum() {
        int sum = 0;
        for (int i = 0; i < this.size(); ++i) {
            sum += ((WeightedResult)this.get((int)i)).weight;
        }
        return sum;
    }

    public E put(Random random) {
        int sum = this.getWeightSum();
        if (sum <= 0) {
            return ((WeightedResult)this.get((int)random.nextInt((int)this.size()))).result;
        }
        int pos = random.nextInt(sum);
        sum = 0;
        for (int i = 0; i < this.size(); ++i) {
            if ((sum += ((WeightedResult)this.get((int)i)).weight) <= pos) continue;
            return ((WeightedResult)this.get((int)i)).result;
        }
        return null;
    }
}

