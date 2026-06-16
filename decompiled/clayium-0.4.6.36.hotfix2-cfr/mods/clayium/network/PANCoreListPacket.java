/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTSizeTracker
 *  net.minecraft.nbt.NBTTagCompound
 */
package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import mods.clayium.block.tile.TilePANCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

public class PANCoreListPacket
implements IMessage {
    public int windowId;
    public Set<TilePANCore.ItemStackWithEnergy> ingreds;
    public Set<TilePANCore.ItemStackWithEnergy> prohibiteds;

    public PANCoreListPacket() {
    }

    public PANCoreListPacket(int windowId, TilePANCore panCore) {
        this.windowId = windowId;
        this.ingreds = panCore.getIngredientItemSet();
        this.prohibiteds = panCore.getProhibitedItemSet();
    }

    public void fromBytes(ByteBuf buf) {
        this.windowId = buf.readInt();
        try {
            this.ingreds = PANCoreListPacket.readSetFromBuffer(buf);
            this.prohibiteds = PANCoreListPacket.readSetFromBuffer(buf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.windowId);
        try {
            PANCoreListPacket.writeSetToBuffer(this.ingreds, buf);
            PANCoreListPacket.writeSetToBuffer(this.prohibiteds, buf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSetToBuffer(Set<TilePANCore.ItemStackWithEnergy> set, ByteBuf buf) throws IOException {
        if (set != null) {
            buf.writeInt(set.size());
            for (TilePANCore.ItemStackWithEnergy item : set) {
                PANCoreListPacket.writeItemStackToBuffer(item.itemstack, buf);
                buf.writeDouble(item.cost);
                buf.writeDouble(item.consumption);
            }
        }
    }

    public static Set<TilePANCore.ItemStackWithEnergy> readSetFromBuffer(ByteBuf buf) throws IOException {
        TreeSet<TilePANCore.ItemStackWithEnergy> set = new TreeSet<TilePANCore.ItemStackWithEnergy>(new TilePANCore.ItemStackComparator());
        int size = buf.readInt();
        for (int i = 0; i < size; ++i) {
            ItemStack itemstack = PANCoreListPacket.readItemStackFromBuffer(buf);
            double cost = buf.readDouble();
            double consumption = buf.readDouble();
            set.add(new TilePANCore.ItemStackWithEnergy(itemstack, cost, consumption));
        }
        return set;
    }

    public static void writeItemStackToBuffer(ItemStack p_150788_1_, ByteBuf buf) throws IOException {
        if (p_150788_1_ == null) {
            buf.writeShort(-1);
        } else {
            buf.writeShort(Item.func_150891_b((Item)p_150788_1_.func_77973_b()));
            buf.writeByte(p_150788_1_.field_77994_a);
            buf.writeShort(p_150788_1_.func_77960_j());
            NBTTagCompound nbttagcompound = null;
            if (p_150788_1_.func_77973_b().func_77645_m() || p_150788_1_.func_77973_b().func_77651_p()) {
                nbttagcompound = p_150788_1_.field_77990_d;
            }
            PANCoreListPacket.writeNBTTagCompoundToBuffer(nbttagcompound, buf);
        }
    }

    public static ItemStack readItemStackFromBuffer(ByteBuf buf) throws IOException {
        ItemStack itemstack = null;
        short short1 = buf.readShort();
        if (short1 >= 0) {
            byte b0 = buf.readByte();
            short short2 = buf.readShort();
            itemstack = new ItemStack(Item.func_150899_d((int)short1), (int)b0, (int)short2);
            itemstack.field_77990_d = PANCoreListPacket.readNBTTagCompoundFromBuffer(buf);
        }
        return itemstack;
    }

    public static void writeNBTTagCompoundToBuffer(NBTTagCompound p_150786_1_, ByteBuf buf) throws IOException {
        if (p_150786_1_ == null) {
            buf.writeShort(-1);
        } else {
            byte[] abyte = CompressedStreamTools.func_74798_a((NBTTagCompound)p_150786_1_);
            buf.writeShort((int)((short)abyte.length));
            buf.writeBytes(abyte);
        }
    }

    public static NBTTagCompound readNBTTagCompoundFromBuffer(ByteBuf buf) throws IOException {
        short short1 = buf.readShort();
        if (short1 < 0) {
            return null;
        }
        byte[] abyte = new byte[short1];
        buf.readBytes(abyte);
        return CompressedStreamTools.func_152457_a((byte[])abyte, (NBTSizeTracker)new NBTSizeTracker(0x200000L));
    }
}

