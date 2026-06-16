/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.block.tile;

import mods.clayium.gui.container.ContainerDummy;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingInTile
extends InventoryCrafting {
    protected int inventoryX;
    protected int inventoryY;
    protected static InventoryCraftingInTile globalGrid;

    public InventoryCraftingInTile(int x, int y) {
        super((Container)new ContainerDummy(), x, y);
        this.inventoryX = x;
        this.inventoryY = y;
    }

    public void setItemFromInventory(ItemStack[] inventory, int index) {
        int j = 0;
        for (int yy = 0; yy < this.inventoryX; ++yy) {
            for (int xx = 0; xx < this.inventoryY; ++xx) {
                this.func_70299_a(j, inventory[index + j]);
                ++j;
            }
        }
    }

    public void setItemFromInventory(IInventory inventory, int index) {
        int j = 0;
        for (int yy = 0; yy < this.inventoryX; ++yy) {
            for (int xx = 0; xx < this.inventoryY; ++xx) {
                this.func_70299_a(j, inventory.func_70301_a(index + j));
                ++j;
            }
        }
    }

    public InventoryCraftingInTile(int x, int y, ItemStack[] inventory, int index) {
        this(x, y);
        this.setItemFromInventory(inventory, index);
    }

    public InventoryCraftingInTile(int x, int y, ItemStack[] inventory) {
        this(x, y, inventory, 0);
    }

    public InventoryCraftingInTile(int x, int y, IInventory inventory, int index) {
        this(x, y);
        this.setItemFromInventory(inventory, index);
    }

    public InventoryCraftingInTile(int x, int y, IInventory inventory) {
        this(x, y, inventory, 0);
    }

    public static InventoryCraftingInTile getNormalCraftingGrid() {
        if (globalGrid == null) {
            globalGrid = new InventoryCraftingInTile(3, 3);
        }
        return globalGrid;
    }

    public static InventoryCraftingInTile getNormalCraftingGrid(ItemStack[] inventory, int index) {
        InventoryCraftingInTile res = InventoryCraftingInTile.getNormalCraftingGrid();
        res.setItemFromInventory(inventory, index);
        return res;
    }

    public static InventoryCraftingInTile getNormalCraftingGrid(ItemStack[] inventory) {
        return InventoryCraftingInTile.getNormalCraftingGrid(inventory, 0);
    }

    public static InventoryCraftingInTile getNormalCraftingGrid(IInventory inventory, int index) {
        InventoryCraftingInTile res = InventoryCraftingInTile.getNormalCraftingGrid();
        res.setItemFromInventory(inventory, index);
        return res;
    }

    public static InventoryCraftingInTile getNormalCraftingGrid(IInventory inventory) {
        return InventoryCraftingInTile.getNormalCraftingGrid(inventory, 0);
    }
}

