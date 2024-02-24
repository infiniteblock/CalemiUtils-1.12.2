package icmoney.event;

import icmoney.ICMoney;
import icmoney.key.KeyBindings;
import icmoney.packet.ServerPacketHandler;
import icmoney.util.helper.CurrencyHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyEvent {

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		if (KeyBindings.openWalletButton.isPressed()) {

			EntityPlayer player = Minecraft.getMinecraft().player;

			ItemStack walletStack = CurrencyHelper.getCurrentWalletStack(player);

			if (!walletStack.isEmpty()) {

				ICMoney.network.sendToServer(new ServerPacketHandler("gui-open%" + ICMoney.guiIdWallet));
			}
		}
	}
}
