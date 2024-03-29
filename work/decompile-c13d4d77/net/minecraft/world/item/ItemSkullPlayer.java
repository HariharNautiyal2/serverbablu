package net.minecraft.world.item;

import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntitySkull;

public class ItemSkullPlayer extends ItemBlockWallable {

    public static final String TAG_SKULL_OWNER = "SkullOwner";

    public ItemSkullPlayer(Block block, Block block1, Item.Info item_info) {
        super(block, block1, item_info, EnumDirection.DOWN);
    }

    @Override
    public IChatBaseComponent getName(ItemStack itemstack) {
        if (itemstack.is(Items.PLAYER_HEAD) && itemstack.hasTag()) {
            String s = null;
            NBTTagCompound nbttagcompound = itemstack.getTag();

            if (nbttagcompound.contains("SkullOwner", 8)) {
                s = nbttagcompound.getString("SkullOwner");
            } else if (nbttagcompound.contains("SkullOwner", 10)) {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("SkullOwner");

                if (nbttagcompound1.contains("Name", 8)) {
                    s = nbttagcompound1.getString("Name");
                }
            }

            if (s != null) {
                return IChatBaseComponent.translatable(this.getDescriptionId() + ".named", s);
            }
        }

        return super.getName(itemstack);
    }

    @Override
    public void verifyTagAfterLoad(NBTTagCompound nbttagcompound) {
        super.verifyTagAfterLoad(nbttagcompound);
        TileEntitySkull.resolveGameProfile(nbttagcompound);
    }
}
