/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.common.network.simpleimpl.IMessageHandler
 *  cpw.mods.fml.common.network.simpleimpl.MessageContext
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 */
package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mods.clayium.gui.client.GuiTemp;
import mods.clayium.gui.container.ContainerTemp;
import mods.clayium.network.GuiTextFieldPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class GuiTextFieldPacketHandler
implements IMessageHandler<GuiTextFieldPacket, IMessage> {
    public IMessage onMessage(GuiTextFieldPacket message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            EntityPlayerMP entityPlayer = ctx.getServerHandler().field_147369_b;
            ContainerTemp container = (ContainerTemp)entityPlayer.field_71070_bA;
            if (message.string != null && message.string.length() >= 1) {
                String s = message.string;
                container.setTextFieldString((EntityPlayer)entityPlayer, s, message.id);
            } else {
                container.setTextFieldString((EntityPlayer)entityPlayer, "", message.id);
            }
        } else {
            GuiScreen g = Minecraft.func_71410_x().field_71462_r;
            if (g instanceof GuiTemp) {
                GuiTemp gui = (GuiTemp)g;
                gui.setTextFieldString(message.string, message.id, true);
            }
        }
        return null;
    }
}

