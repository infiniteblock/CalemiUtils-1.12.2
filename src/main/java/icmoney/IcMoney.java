package icmoney;

import icmoney.command.ICMCommandBase;
import icmoney.config.MarketItemsFile;
import icmoney.event.JoinEvent;
import icmoney.event.KeepWalletEvent;
import icmoney.event.SecurityEvent;
import icmoney.event.WrenchEvent;
import icmoney.gui.base.GuiHandler;
import icmoney.init.InitTileEntities;
import icmoney.packet.MarketPacket;
import icmoney.packet.ServerPacketHandler;
import icmoney.packet.TradingPostPacket;
import icmoney.packet.WalletPacket;
import icmoney.proxy.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ICMReference.MOD_ID, name = ICMReference.MOD_NAME, version = ICMReference.MOD_VERSION)
public class ICMoney {

	@Instance(ICMReference.MOD_ID)
	public static ICMoney instance;

	@SidedProxy(clientSide = ICMReference.CLIENT_PROXY_CLASS, serverSide = ICMReference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	public static final CreativeTabs TAB = new ICMTab();

	public static SimpleNetworkWrapper network;

	public static final int guiIdWallet = 64;
	public static final int guiIdBuildersKit = 65;
	public static final int guiIdInteractionInterfaceFilter = 66;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		MarketItemsFile.init();

		network = NetworkRegistry.INSTANCE.newSimpleChannel(ICMReference.MOD_ID);
		network.registerMessage(ServerPacketHandler.Handler.class, ServerPacketHandler.class, 0, Side.SERVER);
		network.registerMessage(TradingPostPacket.Handler.class, TradingPostPacket.class, 1, Side.SERVER);
		network.registerMessage(MarketPacket.Handler.class, MarketPacket.class, 6, Side.SERVER);
		network.registerMessage(WalletPacket.Handler.class, WalletPacket.class, 7, Side.SERVER);

		MinecraftForge.EVENT_BUS.register(new JoinEvent());
		MinecraftForge.EVENT_BUS.register(new KeepWalletEvent());

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		MinecraftForge.EVENT_BUS.register(new SecurityEvent());
		MinecraftForge.EVENT_BUS.register(new WrenchEvent());

		InitTileEntities.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		proxy.registerRenders();
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {

		event.registerServerCommand(new ICMCommandBase());
	}
}