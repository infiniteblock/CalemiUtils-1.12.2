package icmoney.command;

import icmoney.ICMReference;
import icmoney.config.ICMConfig;
import icmoney.config.MarketItemsFile;
import icmoney.tileentity.TileEntityMarket;
import icmoney.util.helper.ChatHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;

public class ICMCommandBase extends CommandBase {

	@Override
	public String getName() {

		return "icm";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "nothing";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] strings) throws NumberInvalidException {

		if (sender instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) sender;

			if (strings.length == 0) {

				ChatHelper.printModMessage(TextFormatting.GREEN, "----- Help for " + ICMReference.MOD_NAME + " -----",
						player);
				ChatHelper.printModMessage(TextFormatting.GREEN,
						" /icm reload - Reloads the MarketItems Config files.", player);
			}

			else if (strings[0].equals("reload")) {

				MarketItemsFile.init();

				for (TileEntity te : player.world.loadedTileEntityList) {

					if (te instanceof TileEntityMarket) {

						((TileEntityMarket) te).dirtyFlag = true;
					}
				}

				ChatHelper.printModMessage(TextFormatting.GREEN, "Successfully reloaded the files!", player);
			}
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

		return true;
	}

	@Override
	public int getRequiredPermissionLevel() {

		return ICMConfig.misc.usePermission ? 2 : 0;
	}
}
