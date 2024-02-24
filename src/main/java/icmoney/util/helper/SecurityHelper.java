package icmoney.util.helper;

import icmoney.config.CUConfig;
import icmoney.security.ISecurity;
import icmoney.tileentity.base.TileEntityBase;
import icmoney.util.Location;
import icmoney.util.UnitChatMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;

public class SecurityHelper {

	public static boolean openSecuredBlock(Location location, EntityPlayer player, boolean printError) {

		TileEntity tileEntity = location.getTileEntity();

		if (tileEntity instanceof ISecurity) {

			ISecurity security = (ISecurity) tileEntity;

			if (security.getSecurityProfile().isOwner(player.getName()) || player.capabilities.isCreativeMode
					|| !CUConfig.misc.useSecurity) {

				return true;
			}

			else if (printError)
				printErrorMessage(location, player);
		}

		return false;
	}

	public static void printErrorMessage(Location location, EntityPlayer player) {

		UnitChatMessage message = new UnitChatMessage(location.getBlock().getLocalizedName(), player);
		if (!player.world.isRemote)
			message.printMessage(TextFormatting.RED, "This unit doesn't belong to you!");
	}

	public static String getSecuredGuiName(TileEntityBase te) {

		if (te instanceof ISecurity) {
			return " (" + ((ISecurity) te).getSecurityProfile().getOwnerName() + ")";
		}

		return "";
	}
}
