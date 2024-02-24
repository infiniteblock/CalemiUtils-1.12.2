package icmoney.util.helper;

import icmoney.tileentity.base.TileEntityInventoryBase;
import icmoney.util.Location;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class InventoryHelper {

	public static boolean canInsertItem(ItemStack stack, IInventory inventory) {

		for (int i = 0; i < inventory.getSizeInventory(); i++) {

			ItemStack slot = inventory.getStackInSlot(i);

			boolean equalAndNotFull = (ItemStack.areItemsEqual(slot, stack)
					&& slot.getCount() + stack.getCount() < inventory.getInventoryStackLimit());

			if (inventory.isItemValidForSlot(i, stack) && (slot.isEmpty() || equalAndNotFull)) {
				return true;
			}
		}

		return false;
	}

	public static void insertItem(ItemStack stack, IInventory inventory, int slotOffset) {

		for (int i = slotOffset; i < inventory.getSizeInventory(); i++) {

			ItemStack slot = inventory.getStackInSlot(i);

			if (ItemStack.areItemsEqual(slot, stack)
					&& (slot.getCount() + stack.getCount() <= inventory.getInventoryStackLimit())) {

				inventory.setInventorySlotContents(i,
						new ItemStack(stack.getItem(), slot.getCount() + stack.getCount(), stack.getItemDamage()));
				return;
			}

			else if (slot.isEmpty()) {
				inventory.setInventorySlotContents(i, stack);
				return;
			}
		}
	}

	public static void insertItem(ItemStack stack, IInventory inventory) {
		insertItem(stack, inventory, 0);
	}

	public static boolean insertHeldItemIntoSlot(EntityPlayer player, EnumHand hand, Location location,
			IInventory inventory, int slot, boolean removeStack) {

		ItemStack stack = player.getHeldItem(hand);
		TileEntity te = location.getTileEntity();

		if (inventory.getSizeInventory() > slot) {

			if (!stack.isEmpty() && inventory.getStackInSlot(slot).isEmpty()) {

				inventory.setInventorySlotContents(slot, stack.copy());

				if (removeStack) {
					player.setHeldItem(hand, ItemStack.EMPTY);
				}

				if (te instanceof TileEntityInventoryBase) {
					TileEntityInventoryBase teBase = (TileEntityInventoryBase) te;

					teBase.markForUpdate();
				}

				return true;
			}
		}

		return false;
	}

	public static void breakInventory(World world, IInventory inventory, Location location) {

		for (int i = 0; i < inventory.getSizeInventory(); i++) {

			ItemStack stack = inventory.getStackInSlot(i);

			if (!stack.isEmpty()) {

				EntityItem dropEntity = ItemHelper.spawnItem(world, location, stack);

				if (stack.hasTagCompound()) {
					dropEntity.getItem().setTagCompound(stack.getTagCompound());
				}
			}
		}
	}

	public static void consumeItem(IInventory inventory, int amount, boolean suckFromBuildersKit,
			ItemStack... itemStack) {

		consumeItem(0, inventory, amount, suckFromBuildersKit, false, itemStack);
	}

	public static void consumeItem(int slotOffset, IInventory inventory, int amount, boolean suckFromBuildersKit,
			boolean useNBT, ItemStack... itemStacks) {

		int amountLeft = amount;

		if (countItems(inventory, suckFromBuildersKit, useNBT, itemStacks) >= amount) {

			for (int i = slotOffset; i < inventory.getSizeInventory(); i++) {

				if (amountLeft <= 0) {
					break;
				}

				ItemStack stack = inventory.getStackInSlot(i);

				if (!stack.isEmpty()) {

					for (ItemStack itemStack : itemStacks) {

						if (stack.isItemEqual(itemStack)) {

							if (useNBT && itemStack.hasTagCompound()) {

								if (!stack.hasTagCompound()
										|| !stack.getTagCompound().equals(itemStack.getTagCompound())) {
									continue;
								}
							}

							if (amountLeft >= stack.getCount()) {

								amountLeft -= stack.getCount();
								inventory.setInventorySlotContents(i, ItemStack.EMPTY);
							}

							else {

								ItemStack copy = stack.copy();

								inventory.decrStackSize(i, amountLeft);
								amountLeft -= copy.getCount();
							}
						}
					}
				}
			}
		}

	}

	public static int countItems(IInventory inventory, boolean countFromBuildersKit, boolean useNBT,
			ItemStack... itemStacks) {

		int count = 0;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {

			ItemStack stack = inventory.getStackInSlot(i);

			for (ItemStack itemStack : itemStacks) {

				if (stack.isItemEqual(itemStack)) {

					if (useNBT && itemStack.hasTagCompound()) {

						if (stack.hasTagCompound() && stack.getTagCompound().equals(itemStack.getTagCompound())) {
							count += stack.getCount();
						}
					}

					count += stack.getCount();
				}
			}
		}

		return count;
	}
}
