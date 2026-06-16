/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.common.network.simpleimpl.IMessageHandler
 *  cpw.mods.fml.common.network.simpleimpl.MessageContext
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 */
package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mods.clayium.network.KeyInputEventPacket;
import mods.clayium.util.UtilKeyInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class KeyInputEventPacketHandler
implements IMessageHandler<KeyInputEventPacket, IMessage> {
    public IMessage onMessage(KeyInputEventPacket message, MessageContext ctx) {
        EntityPlayerMP entityPlayer = ctx.getServerHandler().field_147369_b;
        UtilKeyInput.setKeyInput((EntityPlayer)ctx.getServerHandler().field_147369_b, message.key, message.keystate);
        return null;
    }
}

