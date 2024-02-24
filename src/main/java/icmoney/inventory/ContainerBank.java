package icmoney.inventory;

import icmoney.init.InitItems;
import icmoney.inventory.base.ContainerBase;
import icmoney.inventory.base.SlotFilter;
import icmoney.tileentity.TileEntityBank;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerBank extends ContainerBase {

	public ContainerBank(EntityPlayer player, TileEntityBank tileEntity) {

		super(player, tileEntity);

		addPlayerInv(8, 62);
		addPlayerHotbar(8, 120);

		addSlotToContainer(new SlotFilter(tileEntity, 0, 62, 18, InitItems.COIN_PENNY, InitItems.COIN_NICKEL,
				InitItems.COIN_QUARTER, InitItems.COIN_DOLLAR));
		addSlotToContainer(new SlotFilter(tileEntity, 1, 98, 18, InitItems.WALLET));
	}
}
