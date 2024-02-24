package icmoney.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import icmoney.config.CUConfig;
import icmoney.item.base.ItemBase;
import icmoney.util.CoinColor;
import icmoney.util.helper.StringHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCurrency extends ItemBase {

	private final String configName;
	public final CoinColor color;
	public final int value;

	public ItemCurrency(String name, String configName, CoinColor color, int value) {

		super("coin_" + name);
		this.configName = configName;
		this.color = color;
		this.value = value;
		if (CUConfig.economy.economy)
			addItem();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		tooltip.add(color.format + configName);

		tooltip.add("Value (1): " + ChatFormatting.GOLD + StringHelper.printCurrency(value));

		if (stack.getCount() > 1) {
			tooltip.add("Value (" + stack.getCount() + "): " + ChatFormatting.GOLD
					+ StringHelper.printCurrency(value * stack.getCount()));
		}
	}
}
