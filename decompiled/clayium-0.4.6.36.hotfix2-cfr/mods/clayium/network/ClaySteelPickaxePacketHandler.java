/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.common.network.simpleimpl.IMessageHandler
 *  cpw.mods.fml.common.network.simpleimpl.MessageContext
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraftforge.common.DimensionManager
 */
package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import java.util.HashMap;
import mods.clayium.network.ClaySteelPickaxePacket;
import mods.clayium.util.UtilAdvancedTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.DimensionManager;

public class ClaySteelPickaxePacketHandler
implements IMessageHandler<ClaySteelPickaxePacket, IMessage> {
    public IMessage onMessage(ClaySteelPickaxePacket message, MessageContext ctx) {
        EntityPlayerMP entityPlayer = ctx.getServerHandler().field_147369_b;
        if (message.side != -2) {
            UtilAdvancedTools.sideList.put((EntityPlayer)entityPlayer, message.side);
        } else {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            map.put("x", message.x);
            map.put("y", message.y);
            map.put("z", message.z);
            map.put("d", message.dimid);
            DimensionManager.getWorld((int)message.dimid).func_147471_g(message.x, message.y, message.z);
            UtilAdvancedTools.forceList.put((EntityPlayer)entityPlayer, map);
        }
        return null;
    }
}

