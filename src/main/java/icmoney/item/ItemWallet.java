package icmoney.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import icmoney.ICMoney;
import icmoney.config.ICMConfig;
import icmoney.init.InitItems;
import icmoney.item.base.ItemBase;
import icmoney.util.helper.InventoryHelper;
import icmoney.util.helper.ItemHelper;
import icmoney.util.helper.LoreHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemWallet extends ItemBase implements IBauble {

	public ItemWallet() {

		super("wallet", 1);
		if (ICMConfig.itemUtils.wallet)
			addItem();
	}

	@Override
	@Optional.Method(modid = "baubles")
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.TRINKET;
	}

	public static boolean isActive(ItemStack stack) {
		return ItemHelper.getNBT(stack).getBoolean("active");
	}

	public static int getBalance(ItemStack stack) {
		return ItemHelper.getNBT(stack).getInteger("balance");
	}

	public void toggleSuck(ItemStack stack) {

		ItemHelper.getNBT(stack).setBoolean("suck", !ItemHelper.getNBT(stack).getBoolean("suck"));
	}

	public static void activate(ItemStack stack, boolean active) {

		ItemHelper.getNBT(stack).setBoolean("active", active);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		LoreHelper.addDisabledLore(tooltip, ICMConfig.wallet.walletCurrencyCapacity);
		LoreHelper.addInformationLore(tooltip, "Used to store currency in one place.");
		LoreHelper.addControlsLore(tooltip, "Open Inventory", LoreHelper.Type.USE, true);
		tooltip.add("");
		tooltip.add(ChatFormatting.AQUA + (isActive(stack) ? "Active" : "Inactive"));
		tooltip.add("Suck: " + ChatFormatting.AQUA + (ItemHelper.getNBT(stack).getBoolean("suck") ? "ON" : "OFF"));
		LoreHelper.addCurrencyLore(tooltip, getBalance(stack), ICMConfig.wallet.walletCurrencyCapacity);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

		if (!worldIn.isRemote && ICMConfig.wallet.walletCurrencyCapacity > 0 && playerIn != null
				&& !playerIn.getHeldItemMainhand().isEmpty()) {

			playerIn.openGui(ICMoney.instance, ICMoney.guiIdWallet, worldIn, (int) playerIn.posX, (int) playerIn.posY,
					(int) playerIn.posZ);
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		tick(stack, entityIn);
	}

	@Override
	@Optional.Method(modid = "baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		tick(stack, player);
	}

	private void tick(ItemStack stack, Entity entityIn) {

		NBTTagCompound nbt = ItemHelper.getNBT(stack);

		if (entityIn.world.getWorldTime() % 2 == 0) {

			if (entityIn instanceof EntityPlayer) {

				EntityPlayer player = (EntityPlayer) entityIn;

				if (ItemHelper.getNBT(stack).getBoolean("suck")) {

					ItemStack[] coinStacks = new ItemStack[] { new ItemStack(InitItems.COIN_PENNY),
							new ItemStack(InitItems.COIN_NICKEL), new ItemStack(InitItems.COIN_QUARTER),
							new ItemStack(InitItems.COIN_DOLLAR) };

					for (ItemStack coinStack : coinStacks) {

						int value = ((ItemCurrency) coinStack.getItem()).value;

						if (InventoryHelper.countItems(player.inventory, false, false, coinStack) > 0
								&& getBalance(stack) + value <= ICMConfig.wallet.walletCurrencyCapacity) {
							InventoryHelper.consumeItem(player.inventory, 1, false, coinStack);
							nbt.setInteger("balance", getBalance(stack) + value);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {

		return ItemHelper.getNBT(stack).getBoolean("active");
	}
}
