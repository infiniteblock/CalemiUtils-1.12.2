package icmoney.init;

import java.util.ArrayList;
import java.util.List;

import icmoney.config.ICMConfig;
import icmoney.item.ItemCurrency;
import icmoney.item.ItemSecurityWrench;
import icmoney.item.ItemWallet;
import icmoney.item.base.ItemBase;
import net.minecraft.item.Item;

public class InitItems {

	public static final List<Item> ITEMS = new ArrayList<>();

	public static final ItemCurrency COIN_PENNY = new ItemCurrency("penny", ICMConfig.economy.pennyName,
			ICMConfig.economy.pennyColor, ICMConfig.economy.pennyValue);
	public static final ItemCurrency COIN_NICKEL = new ItemCurrency("nickel", ICMConfig.economy.nickelName,
			ICMConfig.economy.nickelColor, ICMConfig.economy.nickelValue);
	public static final ItemCurrency COIN_QUARTER = new ItemCurrency("quarter", ICMConfig.economy.quarterName,
			ICMConfig.economy.quarterColor, ICMConfig.economy.quarterValue);
	public static final ItemCurrency COIN_DOLLAR = new ItemCurrency("dollar", ICMConfig.economy.dollarName,
			ICMConfig.economy.dollarColor, ICMConfig.economy.dollarValue);

	public static final Item GOLD_CHIP = new ItemBase("gold_chip").addItem();
	public static final Item SECURITY_WRENCH = new ItemSecurityWrench();
	public static final Item WALLET = new ItemWallet();

}
