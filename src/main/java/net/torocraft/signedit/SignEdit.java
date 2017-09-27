package net.torocraft.signedit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
    modid = SignEdit.MODID,
    version = SignEdit.VERSION,
    name = SignEdit.MODNAME
)
public class SignEdit {

  public static final String MODID = "signedit";
  public static final String VERSION = "1.12.1-4";
  public static final String MODNAME = "SignEdit";

  @EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void editSign(RightClickBlock event) {
    if (event.getEntityPlayer().isSneaking()) {
      return;
    }

    if (!isHoldingSign(event.getEntityPlayer())) {
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
      ObfuscationReflectionHelper.setPrivateValue(TileEntitySign.class, sign, true, "field_145916_j", "isEditable");
      player.openEditSign(sign);
    }
  }

  private static boolean isHoldingSign(EntityPlayer player) {
    for (ItemStack stack : player.getHeldEquipment()) {
      if (stack.getItem() == Items.SIGN) {
        return true;
      }
    }
    return false;
  }

}
