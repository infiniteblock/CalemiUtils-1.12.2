package icmoney.tileentity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import icmoney.config.MarketItemsFile;
import icmoney.security.ISecurity;
import icmoney.security.SecurityProfile;
import icmoney.tileentity.base.ICurrencyNetworkUnit;
import icmoney.tileentity.base.TileEntityBase;
import icmoney.util.Location;
import icmoney.util.helper.CurrencyHelper;
import icmoney.util.helper.InventoryHelper;
import icmoney.util.helper.NetworkHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityMarket extends TileEntityBase implements ISecurity, ICurrencyNetworkUnit {

	private final SecurityProfile profile = new SecurityProfile();

	public final List<MarketItemsFile.MarketItem> marketItemList = new ArrayList<>();
	public boolean buyMode = true;
	public boolean automationMode = false;
	public int selectedOffer;
	public int purchaseAmount = 1;

	private Location bankLocation;

	public boolean dirtyFlag;

	public TileEntityMarket() {
		dirtyFlag = true;
	}

	private void registerMarketItems() {

		marketItemList.clear();
		marketItemList.addAll(MarketItemsFile.registeredBlocks.values());
		marketItemList.sort(Comparator.comparingInt(o -> o.index));
		dirtyFlag = false;

		for (int i = 0; i < marketItemList.size(); i++) {

			if (marketItemList.get(i).isBuy) {
				selectedOffer = i;
				break;
			}
		}

		markForUpdate();
	}

	public MarketItemsFile.MarketItem getSelectedOffer() {

		if (selectedOffer >= marketItemList.size() || selectedOffer < 0) {

			return null;
		}

		else
			return marketItemList.get(selectedOffer);
	}

	@Override
	public Location getBankLocation() {
		return bankLocation;
	}

	@Override
	public void setBankLocation(Location location) {
		this.bankLocation = location;
	}

	public TileEntityBank getBank() {

		TileEntityBank bank = NetworkHelper.getConnectedBank(getLocation(), bankLocation);

		if (bank == null)
			bankLocation = null;

		return bank;
	}

	private IInventory getConnectedInventory() {

		Location location = getLocation().translate(EnumFacing.UP, 1);

		if (location.getTileEntity() != null && !(location.getTileEntity() instanceof TileEntityBank)
				&& location.getTileEntity() instanceof IInventory) {

			return (IInventory) location.getTileEntity();
		}

		return null;
	}

	@Override
	public void update() {

		if (!world.isRemote && dirtyFlag) {
			registerMarketItems();
		}

		MarketItemsFile.MarketItem marketItem = getSelectedOffer();
		TileEntityBank bank = getBank();
		IInventory inv = getConnectedInventory();

		if (world.getWorldTime() % 10 == 0) {

			if (automationMode && marketItem != null && bank != null && inv != null) {

				boolean buyMode = marketItem.isBuy;

				if (buyMode) {

					if (bank.getStoredCurrency() >= marketItem.value) {

						if (InventoryHelper.canInsertItem(marketItem.getStack(), inv)) {

							InventoryHelper.insertItem(marketItem.getStack(), inv);
							bank.addCurrency(-marketItem.value);
						}
					}
				}

				else {

					if (CurrencyHelper.canFitAddedCurrencyToNetwork(bank, marketItem.value)) {

						if (InventoryHelper.countItems(inv, false, false, marketItem.getStack()) >= marketItem.amount) {

							InventoryHelper.consumeItem(inv, marketItem.amount, false, marketItem.getStack());
							bank.addCurrency(marketItem.value);
						}
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound root) {

		buyMode = root.getBoolean("buyMode");
		automationMode = root.getBoolean("autoMode");

		selectedOffer = root.getInteger("selectedOffer");

		purchaseAmount = root.getInteger("purchaseAmount");

		marketItemList.clear();

		if (root.hasKey("MarketItems")) {

			NBTTagCompound marketParent = root.getCompoundTag("MarketItems");

			for (int i = 0; i < marketParent.getSize(); i++) {

				NBTTagCompound itemTag = marketParent.getCompoundTag("Item" + i);

				marketItemList.add(MarketItemsFile.MarketItem.readFromNBT(itemTag));
			}
		}

		super.readFromNBT(root);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound root) {

		NBTTagCompound parent = new NBTTagCompound();

		for (int i = 0; i < marketItemList.size(); i++) {

			MarketItemsFile.MarketItem marketItem = marketItemList.get(i);

			NBTTagCompound marketTag = marketItem.writeToNBT();

			// Write Item to parent
			parent.setTag("Item" + i, marketTag);
		}

		// Write parent to root
		root.setTag("MarketItems", parent);

		root.setBoolean("buyMode", buyMode);
		root.setBoolean("autoMode", automationMode);

		root.setInteger("selectedOffer", selectedOffer);

		root.setInteger("purchaseAmount", purchaseAmount);

		return super.writeToNBT(root);
	}

	@Override
	public EnumFacing[] getConnectedDirections() {

		return EnumFacing.VALUES;
	}

	@Override
	public SecurityProfile getSecurityProfile() {

		return profile;
	}
}
