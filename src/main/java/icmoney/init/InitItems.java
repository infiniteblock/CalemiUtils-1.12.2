package icmoney.init;

import java.util.ArrayList;
import java.util.List;

import icmoney.config.CUConfig;
import icmoney.item.ItemCurrency;
import icmoney.item.ItemSecurityWrench;
import icmoney.item.ItemWallet;
import icmoney.item.base.ItemBase;
import net.minecraft.item.Item;

public class InitItems {

	public static final List<Item> ITEMS = new ArrayList<>();

	public static final ItemCurrency COIN_PENNY = new ItemCurrency("penny", CUConfig.economy.pennyName,
			CUConfig.economy.pennyColor, CUConfig.economy.pennyValue);
	public static final ItemCurrency COIN_NICKEL = new ItemCurrency("nickel", CUConfig.economy.nickelName,
			CUConfig.economy.nickelColor, CUConfig.economy.nickelValue);
	public static final ItemCurrency COIN_QUARTER = new ItemCurrency("quarter", CUConfig.economy.quarterName,
			CUConfig.economy.quarterColor, CUConfig.economy.quarterValue);
	public static final ItemCurrency COIN_DOLLAR = new ItemCurrency("dollar", CUConfig.economy.dollarName,
			CUConfig.economy.dollarColor, CUConfig.economy.dollarValue);

	public static final Item GOLD_CHIP = new ItemBase("gold_chip").addItem();
	public static final Item SECURITY_WRENCH = new ItemSecurityWrench();
	public static final Item WALLET = new ItemWallet();

}
