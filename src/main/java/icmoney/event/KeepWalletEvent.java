package icmoney.event;

import icmoney.config.CUConfig;
import icmoney.item.ItemWallet;
import icmoney.util.Location;
import icmoney.util.helper.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KeepWalletEvent {

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent event) {

		if (CUConfig.wallet.keepWallet) {
			World world = event.getEntityPlayer().world;
			Location location = new Location(world, world.getSpawnPoint());

			if (event.getEntityPlayer().getBedLocation() != null) {
				location = new Location(event.getEntityPlayer().world, event.getEntityPlayer().getBedLocation());
			}

			searchAndSpawn(location, event);
		}
	}

	private void searchAndSpawn(Location location, PlayerDropsEvent event) {

		for (int i = 0; i < event.getDrops().size(); i++) {

			ItemStack stack = event.getDrops().get(i).getItem();

			if (stack.getItem() instanceof ItemWallet) {

				event.getDrops().remove(i);
				ItemHelper.spawnItem(event.getEntityPlayer().world, location, stack);
				searchAndSpawn(location, event);
				return;
			}
		}
	}
}
