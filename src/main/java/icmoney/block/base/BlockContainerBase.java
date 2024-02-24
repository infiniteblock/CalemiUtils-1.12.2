package icmoney.block.base;

import icmoney.ICMoney;
import icmoney.init.InitBlocks;
import icmoney.init.InitItems;
import icmoney.registry.IHasModel;
import icmoney.util.HardnessConstants;
import icmoney.util.MaterialSound;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public abstract class BlockContainerBase extends BlockContainer implements IHasModel {

	BlockContainerBase(String name, MaterialSound matSound, float hardness, int harvestLevel, float resistance) {

		super(matSound.mat);
		setRegistryName(name);
		setTranslationKey(name);
		setSoundType(matSound.sound);
		setHardness(hardness);
		setHarvestLevel("pickaxe", harvestLevel);
		setResistance(resistance);
	}

	protected void addBlock() {

		if (getRegistryName() != null) {
			InitBlocks.BLOCKS.add(this);
			InitItems.ITEMS.add(new ItemBlock(this).setRegistryName(getRegistryName()));
		}

	}

	protected BlockContainerBase(String name, MaterialSound matSound, HardnessConstants constant) {

		this(name, matSound, constant.hardness, constant.harvestLevel, constant.resistance);

		if (!constant.breakable) {
			setBlockUnbreakable();
		}
	}

	@Override
	public void registerModels() {

		ICMoney.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
