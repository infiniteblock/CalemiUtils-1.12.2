package icmoney;

import icmoney.init.InitItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class ICMTab extends CreativeTabs {

	ICMTab() {

		super(ICMReference.MOD_ID + ".tabMain");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack createIcon() {
		ItemStack stack = new ItemStack(InitItems.WALLET);
		return stack;
	}
}
