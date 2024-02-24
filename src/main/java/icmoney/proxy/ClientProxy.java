package icmoney.proxy;

import icmoney.event.KeyEvent;
import icmoney.event.OverlayEvent;
import icmoney.init.InitItems;
import icmoney.item.base.ItemCoinColored;
import icmoney.key.KeyBindings;
import icmoney.render.RenderTradingPost;
import icmoney.tileentity.TileEntityTradingPost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {

		ClientRegistry.registerKeyBinding(KeyBindings.openWalletButton);
		MinecraftForge.EVENT_BUS.register(new KeyEvent());
		MinecraftForge.EVENT_BUS.register(new OverlayEvent());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTradingPost.class, new RenderTradingPost());

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCoinColored(), InitItems.COIN_PENNY);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCoinColored(), InitItems.COIN_NICKEL);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCoinColored(),
				InitItems.COIN_QUARTER);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCoinColored(), InitItems.COIN_DOLLAR);
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {

		if (item.getRegistryName() != null)
			ModelLoader.setCustomModelResourceLocation(item, meta,
					new ModelResourceLocation(item.getRegistryName(), id));
	}
}