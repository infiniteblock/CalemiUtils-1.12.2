package icmoney.command;

import java.util.List;

import javax.annotation.Nullable;

import icmoney.ICMReference;
import icmoney.config.CUConfig;
import icmoney.config.MarketItemsFile;
import icmoney.tileentity.TileEntityMarket;
import icmoney.util.helper.ChatHelper;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class CUCommandBase extends CommandBase {

	private final String[] worldEditOptions = { "cube", "circle", "move" };

	@Override
	public String getName() {

		return "cu";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "nothing";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] strings,
			@Nullable BlockPos targetPos) {

		if (strings.length == 1) {
			return getListOfStringsMatchingLastWord(strings, worldEditOptions);
		}

		if (strings.length == 2) {

			String[] names = new String[EnumDyeColor.values().length];

			for (int i = 0; i < EnumDyeColor.values().length; i++) {
				names[i] = EnumDyeColor.byMetadata(i).getName();
			}

			return getListOfStringsMatchingLastWord(strings, names);
		}

		else {

			return getListOfStringsMatchingLastWord(strings, Block.REGISTRY.getKeys());
		}
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] strings) throws NumberInvalidException {

		if (sender instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) sender;

			if (strings.length == 0) {

				ChatHelper.printModMessage(TextFormatting.GREEN, "----- Help for " + ICMReference.MOD_NAME + " -----",
						player);
				ChatHelper.printModMessage(TextFormatting.GREEN, "() are optional arguments.", player);
				ChatHelper.printModMessage(TextFormatting.GREEN,
						" /cu reload - Reloads the MarketItems and MiningUnitCosts files.", player);
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

		return CUConfig.misc.usePermission ? 2 : 0;
	}
}
