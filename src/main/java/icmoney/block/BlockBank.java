package icmoney.block;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import icmoney.ICMoney;
import icmoney.block.base.BlockInventoryContainerBase;
import icmoney.config.CUConfig;
import icmoney.tileentity.TileEntityBank;
import icmoney.util.HardnessConstants;
import icmoney.util.IExtraInformation;
import icmoney.util.Location;
import icmoney.util.MaterialSound;
import icmoney.util.helper.LoreHelper;
import icmoney.util.helper.SecurityHelper;
import icmoney.util.helper.StringHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBank extends BlockInventoryContainerBase implements IExtraInformation {

	public BlockBank() {

		super("bank", MaterialSound.IRON, HardnessConstants.SECURED);
		setCreativeTab(ICMoney.TAB);
		setBlockUnbreakable();
		if (CUConfig.blockUtils.bank && CUConfig.economy.economy)
			addBlock();
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {

		LoreHelper.addInformationLore(tooltip, "Collects RC from all connected Trading Posts.");
		LoreHelper.addControlsLore(tooltip, "Open Inventory", LoreHelper.Type.USE, true);
	}

	@Override
	public void getButtonInformation(List<String> list, World world, Location location, ItemStack stack) {

		TileEntity te = location.getTileEntity();

		if (te instanceof TileEntityBank) {

			TileEntityBank bank = (TileEntityBank) te;

			list.add("Currency: " + ChatFormatting.GOLD + StringHelper.printCurrency(bank.getStoredCurrency()));
		}
	}

	@Override
	public ItemStack getButtonIcon(World world, Location location, ItemStack stack) {

		return stack;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		Location location = new Location(worldIn, pos);

		if (SecurityHelper.openSecuredBlock(location, playerIn, true)) {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}

		else
			return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityBank();
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {

		return EnumBlockRenderType.MODEL;
	}
}
