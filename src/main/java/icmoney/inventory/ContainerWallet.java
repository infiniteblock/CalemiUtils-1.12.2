package icmoney.inventory;

import icmoney.config.ICMConfig;
import icmoney.init.InitItems;
import icmoney.inventory.base.ContainerBase;
import icmoney.inventory.base.ItemStackInventory;
import icmoney.inventory.base.SlotFilter;
import icmoney.item.ItemCurrency;
import icmoney.util.helper.CurrencyHelper;
import icmoney.util.helper.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerWallet extends ContainerBase {

	private final EntityPlayer player;
	private final ItemStack stack;
	private final ItemStackInventory stackInv;

	public ContainerWallet(EntityPlayer player) {

		super(player, 1);

		isItemContainer = true;

		this.player = player;
		this.stack = player.getHeldItemMainhand();
		stackInv = new ItemStackInventory(stack, size);

		addPlayerInv(8, 94);
		addPlayerHotbar(8, 152);

		// New Inventory
		this.addSlotToContainer(new SlotFilter(stackInv, 0, 17, 42, InitItems.COIN_PENNY, InitItems.COIN_NICKEL,
				InitItems.COIN_QUARTER, InitItems.COIN_DOLLAR));
	}

	private ItemStack getCurrentWalletStack() {
		return CurrencyHelper.getCurrentWalletStack(player, false);
	}

	private NBTTagCompound getNBT() {

		return ItemHelper.getNBT(getCurrentWalletStack());
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {

		ItemStack returnStack = super.slotClick(slotId, dragType, clickTypeIn, player);

		ItemStack stack = stackInv.getStackInSlot(0);

		if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemCurrency) {

			ItemCurrency currency = ((ItemCurrency) stack.getItem());

			int balance = getNBT().getInteger("balance");

			int amountToAdd = 0;
			int stacksToRemove = 0;

			for (int i = 0; i < stack.getCount(); i++) {

				if (balance + currency.value <= ICMConfig.wallet.walletCurrencyCapacity) {

					balance += currency.value;

					amountToAdd += currency.value;
					stacksToRemove++;
				}
			}

			getNBT().setInteger("balance", getNBT().getInteger("balance") + amountToAdd);
			stackInv.decrStackSize(0, stacksToRemove);
		}

		return returnStack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {

		super.onContainerClosed(player);

		stackInv.dump(stack);
	}
}
