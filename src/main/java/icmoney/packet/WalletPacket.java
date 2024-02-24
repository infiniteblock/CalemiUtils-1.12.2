package icmoney.packet;

import java.util.List;

import icmoney.init.InitItems;
import icmoney.item.ItemWallet;
import icmoney.util.helper.CurrencyHelper;
import icmoney.util.helper.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WalletPacket extends ServerPacketHandler {

	public WalletPacket() {

	}

	public WalletPacket(String text) {

		this.text = text;
	}

	public static class Handler implements IMessageHandler<WalletPacket, IMessage> {

		@Override
		public IMessage onMessage(WalletPacket message, MessageContext ctx) {

			String[] data = message.text.split("%");
			EntityPlayer player = ctx.getServerHandler().player;

			if (data[0].equalsIgnoreCase("withdraw")) {

				int id = Integer.parseInt(data[1]);

				int multiplier = Integer.parseInt(data[2]);

				Item item = InitItems.COIN_PENNY;
				int price = InitItems.COIN_PENNY.value;

				if (id == 1) {
					item = InitItems.COIN_NICKEL;
					price = InitItems.COIN_NICKEL.value;
				}
				if (id == 2) {
					item = InitItems.COIN_QUARTER;
					price = InitItems.COIN_QUARTER.value;
				}
				if (id == 3) {
					item = InitItems.COIN_DOLLAR;
					price = InitItems.COIN_DOLLAR.value;
				}

				price *= multiplier;

				ItemStack walletStack = CurrencyHelper.getCurrentWalletStack(player, false);

				if (!walletStack.isEmpty()) {

					NBTTagCompound nbt = ItemHelper.getNBT(walletStack);

					nbt.setInteger("balance", nbt.getInteger("balance") - price);

					ItemHelper.spawnItem(player.world, player, new ItemStack(item, multiplier));
				}
			}

			else if (data[0].equalsIgnoreCase("togglesuck")) {

				ItemStack heldStack = CurrencyHelper.getCurrentWalletStack(player, false);

				if (heldStack.getItem() instanceof ItemWallet) {

					ItemWallet wallet = (ItemWallet) heldStack.getItem();

					wallet.toggleSuck(heldStack);
				}
			}

			else if (data[0].equalsIgnoreCase("activate")) {

				ItemStack heldStack = CurrencyHelper.getCurrentWalletStack(player, false);
				List<ItemStack> list = CurrencyHelper.checkForActiveWallets(player);

				if (heldStack.getItem() instanceof ItemWallet) {

					for (ItemStack stack : list) {

						ItemWallet.activate(stack, false);
					}

					ItemWallet.activate(heldStack, true);
				}
			}

			return null;
		}
	}
}
