package icmoney.item.base;

import icmoney.ICMoney;
import icmoney.init.InitItems;
import icmoney.registry.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IHasModel {

	private boolean hasEffect = false;

	public ItemBase(String name) {
		setRegistryName(name);
		setTranslationKey(name);
		setCreativeTab(ICMoney.TAB);
	}

	protected ItemBase(String name, int stackSize) {

		this(name);
		setMaxStackSize(stackSize);
	}

	public ItemBase(String name, boolean shouldRegister) {
		setRegistryName(name);
		setTranslationKey(name);
		setCreativeTab(ICMoney.TAB);

		if (shouldRegister)
			addItem();
	}

	public ItemBase addItem() {

		InitItems.ITEMS.add(this);
		return this;
	}

	public ItemBase setEffect() {
		hasEffect = true;
		return this;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {

		return hasEffect;
	}

	@Override
	public void registerModels() {

		ICMoney.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
