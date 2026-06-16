/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.lib.vec.BlockCoord
 *  codechicken.multipart.MultiPartRegistry
 *  codechicken.multipart.MultiPartRegistry$IPartConverter
 *  codechicken.multipart.MultiPartRegistry$IPartFactory
 *  codechicken.multipart.TMultiPart
 *  net.minecraft.block.Block
 *  net.minecraft.world.World
 */
package mods.clayium.plugin.multipart;

import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import java.util.Arrays;
import mods.clayium.block.CBlocks;
import mods.clayium.plugin.multipart.PANCablePart;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class RegisterMultipart
implements MultiPartRegistry.IPartFactory,
MultiPartRegistry.IPartConverter {
    public TMultiPart createPart(String name, boolean client) {
        if (name.equals(PANCablePart.type)) {
            return new PANCablePart();
        }
        return null;
    }

    public void init() {
        MultiPartRegistry.registerConverter((MultiPartRegistry.IPartConverter)this);
        MultiPartRegistry.registerParts((MultiPartRegistry.IPartFactory)this, (String[])new String[]{PANCablePart.type});
    }

    public Iterable<Block> blockTypes() {
        return Arrays.asList(CBlocks.blockPANCable);
    }

    public TMultiPart convert(World world, BlockCoord pos) {
        Block b = world.func_147439_a(pos.x, pos.y, pos.z);
        if (b == CBlocks.blockPANCable) {
            return new PANCablePart();
        }
        return null;
    }
}

