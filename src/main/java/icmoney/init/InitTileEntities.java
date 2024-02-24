package icmoney.init;

import icmoney.ICMReference;
import icmoney.tileentity.TileEntityBank;
import icmoney.tileentity.TileEntityMarket;
import icmoney.tileentity.TileEntityNetworkCable;
import icmoney.tileentity.TileEntityNetworkGate;
import icmoney.tileentity.TileEntityTradingPost;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitTileEntities {

	public static void init() {

		GameRegistry.registerTileEntity(TileEntityBank.class,
				new ResourceLocation(ICMReference.MOD_ID + ":tileEntityBank"));
		GameRegistry.registerTileEntity(TileEntityNetworkCable.class,
				new ResourceLocation(ICMReference.MOD_ID + ":tileEntityNetworkCable"));
		GameRegistry.registerTileEntity(TileEntityNetworkGate.class,
				new ResourceLocation(ICMReference.MOD_ID + ":tileEntityNetworkGate"));
		GameRegistry.registerTileEntity(TileEntityTradingPost.class,
				new ResourceLocation(ICMReference.MOD_ID + ":tileEntityTradingPost"));
		GameRegistry.registerTileEntity(TileEntityMarket.class,
				new ResourceLocation(ICMReference.MOD_ID + ":tileEntityMarket"));
	}
}
