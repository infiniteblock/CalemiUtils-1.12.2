package icmoney.util;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IExtraInformation {

	void getButtonInformation(List<String> list, World world, Location location, ItemStack stack);

	ItemStack getButtonIcon(World world, Location location, ItemStack stack);
}
