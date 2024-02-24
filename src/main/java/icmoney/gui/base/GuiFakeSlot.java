package icmoney.gui.base;

import java.util.List;

import org.lwjgl.opengl.GL11;

import icmoney.util.helper.GuiHelper;
import icmoney.util.helper.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFakeSlot extends GuiButton {

	private final GuiRect rect;
	private final RenderItem itemRender;
	private ItemStack stack = new ItemStack(Blocks.AIR);

	public GuiFakeSlot(int id, int x, int y, RenderItem itemRender, List<GuiButton> buttonList) {

		super(id, x, y, 16, 16, "");
		rect = new GuiRect(this.x, this.y, width, height);
		this.itemRender = itemRender;
		buttonList.add(this);
	}

	private ItemStack getItemStack() {

		return stack;
	}

	public void setItemStack(ItemStack stack) {

		this.stack = stack;
	}

	public void renderButton(int mouseX, int mouseY, int zLevel) {

		if (this.visible) {

			List<String> list = StringHelper.removeNullsFromList(
					stack.getTooltip(Minecraft.getMinecraft().player, ITooltipFlag.TooltipFlags.NORMAL));

			StringHelper.removeCharFromList(list, "Shift", "Ctrl");

			GuiHelper.drawItemStack(itemRender, getItemStack(), rect.x, rect.y);
			if (!stack.isEmpty())
				GuiHelper.drawHoveringTextBox(mouseX, mouseY, zLevel, rect, StringHelper.getArrayFromList(list));

			GL11.glColor3f(1, 1, 1);
		}
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

	}
}
