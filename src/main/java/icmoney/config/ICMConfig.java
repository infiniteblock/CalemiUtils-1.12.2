package icmoney.config;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import icmoney.ICMReference;
import icmoney.util.CoinColor;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ICMReference.MOD_ID, name = "ICMoney/ICMoney")
@Config.LangKey("config.title")
public class ICMConfig implements IConditionFactory {

	public static final CategoryItemUtils itemUtils = new CategoryItemUtils();
	public static final CategoryBlockUtils blockUtils = new CategoryBlockUtils();
	public static final CategoryTooltips tooltips = new CategoryTooltips();
	public static final CategoryBlockScans blockScans = new CategoryBlockScans();
	public static final CategoryEconomy economy = new CategoryEconomy();
	public static final CategoryWallet wallet = new CategoryWallet();
	public static final CategoryMisc misc = new CategoryMisc();

	private static final String NEEDED_FOR_SERVERS = "(Only needed on Servers)";

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {

		return () -> economy.currencyRecipes;
	}

	public static class CategoryItemUtils {

		@Name("Enable Wallet")
		@Config.Comment("Disable this to remove the Wallet.")
		public boolean wallet = true;

		@Name("Enable Security Wrench")
		@Config.Comment("Disable this to remove the Security Wrench.")
		public boolean securityWrench = true;

	}

	public static class CategoryBlockScans {

		@Name("Vein Scan Max Size")
		@Config.Comment("The Vein Scan is a system used by Networks. It scans for blocks in a chain. The max size is how many chains will occur. Lower values run faster on servers.")
		@RangeInt(min = 0, max = 1500)
		public int veinScanMaxSize = 1500;
	}

	public static class CategoryBlockUtils {

		@Name("Enable Bank")
		@Config.Comment("Disable this to remove the Bank.")
		public boolean bank = true;

		@Name("Enable Network Cable & Gate")
		@Config.Comment("Disable this to remove Network Cable & Gate.")
		public boolean networkCable = true;

		@Name("Enable Trading Post")
		@Config.Comment("Disable this to remove the Trading Post.")
		public boolean tradingPost = true;

		@Name("Enable Market")
		@Config.Comment("Disable this to remove the Market.")
		public boolean market = true;
	}

	public static class CategoryTooltips {

		@Name("Show Information on Tooltips")
		public boolean showInfoOnTooltips = true;

		@Name("Show Controls on Tooltips")
		public boolean showControlsOnTooltips = true;
	}

	public static class CategoryEconomy {
		
		@Name("Webhook Head URL")
		@Config.Comment("Player Head Url. - default = https://mc-heads.net/avatar/")
		public String AvatarURL = "https://mc-heads.net/avatar/";

		@Name("Webhook URL")
		@Config.Comment("Discord Webhook URL.")
		public String webhookURL = "";

		@Name("Enable Discord Integration")
		@Config.Comment("Disable this to not advertise items listed for sale on Discord.")
		public boolean discord = true;

		@Name("Enable Economy")
		@Config.Comment("Disable this to remove Economy and everything that uses it.")
		public boolean economy = true;

		@Name("Enable Coin Recipes")
		@Config.Comment("Disable this to remove the recipes for the coins.")
		boolean currencyRecipes = true;

		@Name("Currency Name")
		@Config.Comment("Edit this name to change the name of the currency for everything. Try to keep it small.")
		public String currencyName = "RC";

		@Name("Penny Name")
		@Config.Comment("Edit this name to change the name of the Penny.")
		public String pennyName = "Penny";

		@Name("Penny Color")
		@Config.Comment("Edit this name to change the color of the Penny.")
		public CoinColor pennyColor = CoinColor.BRONZE;

		@Name("Penny Value")
		@Config.Comment("Edit this name to change the value of the Penny.")
		@RangeInt(min = 0, max = 10000)
		public int pennyValue = 1;

		@Name("Nickel Name")
		@Config.Comment("Edit this name to change the name of the Nickel.")
		public String nickelName = "Nickel";

		@Name("Nickel Color")
		@Config.Comment("Edit this name to change the color of the Nickel.")
		public CoinColor nickelColor = CoinColor.GRAY;

		@Name("Nickel Value")
		@Config.Comment("Edit this name to change the value of the Nickel.")
		@RangeInt(min = 0, max = 10000)
		public int nickelValue = 5;

		@Name("Quarter Name")
		@Config.Comment("Edit this name to change the name of the Quarter.")
		public String quarterName = "Quarter";

		@Name("Quarter Color")
		@Config.Comment("Edit this name to change the color of the Quarter.")
		public CoinColor quarterColor = CoinColor.SILVER;

		@Name("Quarter Value")
		@Config.Comment("Edit this name to change the value of the Quarter.")
		@RangeInt(min = 0, max = 10000)
		public int quarterValue = 25;

		@Name("Dollar Name")
		@Config.Comment("Edit this name to change the name of the Dollar.")
		public String dollarName = "Dollar";

		@Name("Dollar Color")
		@Config.Comment("Edit this name to change the color of the Dollar.")
		public CoinColor dollarColor = CoinColor.GOLD;

		@Name("Dollar Value")
		@Config.Comment("Edit this name to change the value of the Dollar.")
		@RangeInt(min = 0, max = 10000)
		public int dollarValue = 100;
	}

	public static class CategoryWallet {

		@Name("Wallet Currency Capacity")
		@Config.Comment("The max amount of currency the Wallet can store.")
		@RangeInt(min = 0, max = 99999999)
		public int walletCurrencyCapacity = 99999999;

		@Name("Give Starting Wallet")
		@Config.Comment("Enable this to give players a wallet the first time they join the world.")
		public boolean startingWallet = false;

		@Name("Keep Wallets on Death")
		@Config.Comment("Enable this to spawn any wallets at the player spawnpoint when they die.")
		public boolean keepWallet = false;

		@Name("Render Wallet Currency Overlay")
		@Config.Comment("Enable this render an overlay on your game screen showing your wallet stats.")
		public boolean walletOverlay = true;
	}

	public static class CategoryMisc {

		@Name("Use Permission for Commands")
		@Config.Comment(NEEDED_FOR_SERVERS + " Enable this to restrict non-ops from using cu commands.")
		public boolean usePermission = false;

		@Name("Use Security")
		@Config.Comment("Disable this to allow everyone access to anyone's blocks.")
		public boolean useSecurity = true;

		@Name("Bank Currency Capacity")
		@Config.Comment("The max amount of currency the Bank can store.")
		@RangeInt(min = 0)
		public int bankCurrencyCapacity = 1000000;

		@Name("Trading Post Currency Capacity")
		@Config.Comment("The max amount of currency the Trading Post can store.")
		@RangeInt(min = 0)
		public int postCurrencyCapacity = 1000000;
	}

	@Mod.EventBusSubscriber(modid = ICMReference.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {

			if (event.getModID().equals(ICMReference.MOD_ID)) {
				ConfigManager.sync(ICMReference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}