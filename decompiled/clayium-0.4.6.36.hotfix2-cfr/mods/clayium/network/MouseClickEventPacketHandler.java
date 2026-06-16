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
import mods.clayium.network.MouseClickEventPacket;
import mods.clayium.util.UtilKeyInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class MouseClickEventPacketHandler
implements IMessageHandler<MouseClickEventPacket, IMessage> {
    public IMessage onMessage(MouseClickEventPacket message, MessageContext ctx) {
        EntityPlayerMP entityPlayer = ctx.getServerHandler().field_147369_b;
        UtilKeyInput.setMouseInput((EntityPlayer)ctx.getServerHandler().field_147369_b, message.button, message.buttonstate);
        return null;
    }
}

