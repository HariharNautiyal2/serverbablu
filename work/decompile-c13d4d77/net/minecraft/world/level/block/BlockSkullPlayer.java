package net.minecraft.world.level.block;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockSkullPlayer extends BlockSkull {

    public static final MapCodec<BlockSkullPlayer> CODEC = simpleCodec(BlockSkullPlayer::new);

    @Override
    public MapCodec<BlockSkullPlayer> codec() {
        return BlockSkullPlayer.CODEC;
    }

    protected BlockSkullPlayer(BlockBase.Info blockbase_info) {
        super(BlockSkull.Type.PLAYER, blockbase_info);
    }

    @Override
    public void setPlacedBy(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
        super.setPlacedBy(world, blockposition, iblockdata, entityliving, itemstack);
        TileEntity tileentity = world.getBlockEntity(blockposition);

        if (tileentity instanceof TileEntitySkull) {
            TileEntitySkull tileentityskull = (TileEntitySkull) tileentity;
            GameProfile gameprofile = null;

            if (itemstack.hasTag()) {
                NBTTagCompound nbttagcompound = itemstack.getTag();

                if (nbttagcompound.contains("SkullOwner", 10)) {
                    gameprofile = GameProfileSerializer.readGameProfile(nbttagcompound.getCompound("SkullOwner"));
                } else if (nbttagcompound.contains("SkullOwner", 8) && !SystemUtils.isBlank(nbttagcompound.getString("SkullOwner"))) {
                    gameprofile = new GameProfile(SystemUtils.NIL_UUID, nbttagcompound.getString("SkullOwner"));
                }
            }

            tileentityskull.setOwner(gameprofile);
        }

    }
}
