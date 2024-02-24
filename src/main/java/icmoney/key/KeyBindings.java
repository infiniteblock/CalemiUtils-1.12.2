package icmoney.key;

import org.lwjgl.input.Keyboard;

import icmoney.ICMReference;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {

	public static final KeyBinding openWalletButton = new KeyBinding("Open Wallet", Keyboard.KEY_G,
			ICMReference.MOD_NAME);
}
