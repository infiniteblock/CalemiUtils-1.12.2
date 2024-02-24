package icmoney.gui.base;

import icmoney.ICMoney;
import icmoney.gui.GuiWallet;
import icmoney.inventory.ContainerWallet;
import icmoney.tileentity.base.ITileEntityGuiHandler;
import icmoney.util.Location;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		Location loc = new Location(world, x, y, z);

		TileEntity tileentity = loc.getTileEntity();

		if (ID == ICMoney.guiIdWallet) {
			return new ContainerWallet(player);
		}

		if (tileentity instanceof ITileEntityGuiHandler) {
			return ((ITileEntityGuiHandler) tileentity).getTileContainer(player);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		Location loc = new Location(world, x, y, z);

		TileEntity tileentity = loc.getTileEntity();

		if (ID == ICMoney.guiIdWallet) {
			return new GuiWallet(player);
		}

		if (tileentity instanceof ITileEntityGuiHandler) {
			return ((ITileEntityGuiHandler) tileentity).getTileGuiContainer(player);
		}

		return null;
	}
}
