package icmoney.event;

import icmoney.security.ISecurity;
import icmoney.tileentity.base.TileEntityBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SecurityEvent {

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {

		TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());

		if (tileEntity instanceof TileEntityBase && tileEntity instanceof ISecurity) {

			ISecurity security = (ISecurity) tileEntity;

			security.getSecurityProfile().setOwner(event.getPlayer());
			((TileEntityBase) tileEntity).markForUpdate();
		}
	}
}
