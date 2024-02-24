package icmoney.inventory;

import icmoney.inventory.base.ContainerBase;
import icmoney.tileentity.TileEntityTradingPost;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerTradingPost extends ContainerBase {

	public ContainerTradingPost(EntityPlayer player, TileEntityTradingPost tileEntity) {

		super(player, tileEntity);

		addPlayerInv(8, 141);
		addPlayerHotbar(8, 199);

		addTileEntityStorageInv(tileEntity, 0, 8, 83, 3);
	}
}
