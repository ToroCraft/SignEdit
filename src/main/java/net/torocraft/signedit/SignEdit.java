package net.torocraft.signedit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = SignEdit.MODID, version = SignEdit.VERSION, name = SignEdit.MODNAME)
public class SignEdit {

  public static final String MODID = "signedit";
  public static final String VERSION = "1.11.2-2";
  public static final String MODNAME = "SignEdit";

  @EventBusSubscriber
  public static class DiscoveryHandler {

    @SubscribeEvent
    public static void editSign(RightClickBlock event) {
      if (event.getEntityPlayer().isSneaking()) {
        return;
      }

      BlockPos pos = new BlockPos(event.getHitVec());
      IBlockState state = event.getWorld().getBlockState(pos);

      if (state.getBlock() != Blocks.WALL_SIGN && state.getBlock() != Blocks.STANDING_SIGN) {
        return;
      }

      EntityPlayer player = event.getEntityPlayer();
      TileEntity tileentity = event.getWorld().getTileEntity(pos);

      if (tileentity instanceof TileEntitySign) {
        TileEntitySign sign = (TileEntitySign) tileentity;
        sign.setPlayer(player);
        sign.setEditable(true);
        player.openEditSign(sign);
      }
    }

  }
}
