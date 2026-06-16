/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.gameevent.InputEvent$KeyInputEvent
 *  cpw.mods.fml.common.gameevent.TickEvent$ClientTickEvent
 *  cpw.mods.fml.common.gameevent.TickEvent$Phase
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.MouseEvent
 */
package mods.clayium.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import mods.clayium.core.ClayiumCore;
import mods.clayium.network.KeyInputEventPacket;
import mods.clayium.network.MouseClickEventPacket;
import mods.clayium.util.PlayerKey;
import mods.clayium.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;

public class UtilKeyInput {
    public static UtilKeyInput INSTANCE = new UtilKeyInput();
    @SideOnly(value=Side.CLIENT)
    public static volatile HashMap<KeyBinding, PlayerKey.KeyType> keyBindingMap;
    public static HashMap<EntityPlayer, PlayerKey> playerKeyMapClient;
    public static HashMap<EntityPlayer, PlayerKey> playerKeyMap;

    public void playerTick(EntityPlayer player) {
        if (!UtilKeyInput.keyMapContainsKey(player)) {
            UtilKeyInput.keyMapPut(player, new PlayerKey());
        }
        UtilKeyInput.keyMapGet(player).update();
    }

    @Deprecated
    @SideOnly(value=Side.CLIENT)
    @SubscribeEvent
    public void MouseEventSubscriber(MouseEvent event) {
        if (event.button != -1) {
            UtilKeyInput.setMouseInput(UtilPlayer.getClientPlayer(), event.button, event.buttonstate);
            ClayiumCore.packetDispatcher.sendToServer((IMessage)new MouseClickEventPacket(event.button, event.buttonstate));
        }
    }

    @Deprecated
    @SideOnly(value=Side.CLIENT)
    public void sendMouseEventToServer(int button, boolean buttonstate) {
        EntityPlayer player = UtilPlayer.getClientPlayer();
        if (button != -1 && player != null) {
            boolean oldstate;
            boolean bl = oldstate = UtilKeyInput.getMouseLength(player, button) >= 0;
            if (oldstate != buttonstate) {
                UtilKeyInput.setMouseInput(player, button, buttonstate);
                ClayiumCore.packetDispatcher.sendToServer((IMessage)new MouseClickEventPacket(button, buttonstate));
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    @SubscribeEvent
    public void ClientTickEventSubscriber(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (Minecraft.func_71410_x().field_71462_r != null) {
                this.sendMouseEventToServer(0, false);
                this.sendMouseEventToServer(1, false);
            }
            this.sendAllKeyEventToServer();
        }
    }

    @SideOnly(value=Side.CLIENT)
    @SubscribeEvent
    public void KeyEventSubscriber(InputEvent.KeyInputEvent event) {
    }

    @SideOnly(value=Side.CLIENT)
    public synchronized void sendAllKeyEventToServer() {
        if (keyBindingMap == null) {
            keyBindingMap = new HashMap();
            keyBindingMap.put(Minecraft.func_71410_x().field_71474_y.field_151444_V, PlayerKey.KeyType.SPRINT);
            keyBindingMap.put(Minecraft.func_71410_x().field_71474_y.field_74313_G, PlayerKey.KeyType.USE_ITEM);
        }
        for (KeyBinding key : keyBindingMap.keySet()) {
            this.sendKeyEventToServer(key);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void sendKeyEventToServer(PlayerKey.KeyType key, boolean buttonstate) {
        if (key == null) {
            return;
        }
        EntityPlayer player = UtilPlayer.getClientPlayer();
        if (player != null) {
            boolean oldstate;
            boolean bl = oldstate = UtilKeyInput.getKeyLength(player, key) >= 0;
            if (oldstate != buttonstate) {
                UtilKeyInput.setKeyInput(player, key, buttonstate);
                ClayiumCore.packetDispatcher.sendToServer((IMessage)new KeyInputEventPacket(key, buttonstate));
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void sendKeyEventToServer(KeyBinding key) {
        this.sendKeyEventToServer(keyBindingMap.get(key), key.func_151470_d());
    }

    public static PlayerKey keyMapGet(EntityPlayer player) {
        return UtilPlayer.isRemote(player) ? playerKeyMapClient.get(player) : playerKeyMap.get(player);
    }

    public static PlayerKey keyMapPut(EntityPlayer player, PlayerKey value) {
        return UtilPlayer.isRemote(player) ? playerKeyMapClient.put(player, value) : playerKeyMap.put(player, value);
    }

    public static boolean keyMapContainsKey(EntityPlayer player) {
        return UtilPlayer.isRemote(player) ? playerKeyMapClient.containsKey(player) : playerKeyMap.containsKey(player);
    }

    public static int getKeyLength(EntityPlayer player, PlayerKey.KeyType key) {
        if (!UtilKeyInput.keyMapContainsKey(player)) {
            UtilKeyInput.keyMapPut(player, new PlayerKey());
        }
        return UtilKeyInput.keyMapGet(player).getKeyLength(key);
    }

    public static void setKeyInput(EntityPlayer player, PlayerKey.KeyType key, boolean buttonstate) {
        if (!UtilKeyInput.keyMapContainsKey(player)) {
            UtilKeyInput.keyMapPut(player, new PlayerKey());
        }
        UtilKeyInput.keyMapGet(player).setKeyInput(key, buttonstate);
    }

    @Deprecated
    public static int getMouseLength(EntityPlayer player, int button) {
        if (!UtilKeyInput.keyMapContainsKey(player)) {
            UtilKeyInput.keyMapPut(player, new PlayerKey());
        }
        return UtilKeyInput.keyMapGet(player).getMouseLength(button);
    }

    @Deprecated
    public static void setMouseInput(EntityPlayer player, int button, boolean buttonstate) {
        if (!UtilKeyInput.keyMapContainsKey(player)) {
            UtilKeyInput.keyMapPut(player, new PlayerKey());
        }
        UtilKeyInput.keyMapGet(player).setMouseInput(button, buttonstate);
    }

    static {
        playerKeyMapClient = new HashMap();
        playerKeyMap = new HashMap();
    }
}

