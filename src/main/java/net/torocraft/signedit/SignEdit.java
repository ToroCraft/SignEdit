package net.torocraft.signedit;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod(SignEdit.MODID)
public class SignEdit {

  public static final String MODID = "signedit";

  public SignEdit() {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void editSign(RightClickBlock event) {
    if (event.getPlayer().isSneaking()) {
      return;
    }

    if (!isHoldingEditor(event.getPlayer())) {
      return;
    }

    BlockPos pos = new BlockPos(event.getPos());
    BlockState state = event.getWorld().getBlockState(pos);

    if (!(state.getBlock() instanceof AbstractSignBlock)) {
      return;
    }

    PlayerEntity player = event.getPlayer();
    TileEntity tileentity = event.getWorld().getTileEntity(pos);

    if (tileentity instanceof SignTileEntity) {
      SignTileEntity sign = (SignTileEntity) tileentity;
      sign.setPlayer(player);
      ObfuscationReflectionHelper.setPrivateValue(SignTileEntity.class, sign, true,
          "field_145916_j");
      player.openSignEditor(sign);
    }
  }

  private static boolean isHoldingEditor(Entity player) {
    for (ItemStack stack : player.getHeldEquipment()) {
      if (stack.getItem() instanceof SignItem) {
        return true;
      }
    }
    return false;
  }

}
