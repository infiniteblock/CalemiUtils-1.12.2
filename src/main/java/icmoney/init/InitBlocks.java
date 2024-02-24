package icmoney.init;

import java.util.ArrayList;
import java.util.List;

import icmoney.block.BlockBank;
import icmoney.block.BlockMarket;
import icmoney.block.BlockNetworkCable;
import icmoney.block.BlockNetworkCableOpaque;
import icmoney.block.BlockNetworkGate;
import icmoney.block.BlockTradingPost;
import net.minecraft.block.Block;

public class InitBlocks {

	public static final List<Block> BLOCKS = new ArrayList<>();

	public static final Block BANK = new BlockBank();
	public static final Block NETWORK_CABLE = new BlockNetworkCable();
	public static final Block NETWORK_CABLE_OPAQUE = new BlockNetworkCableOpaque();
	public static final Block NETWORK_GATE = new BlockNetworkGate(true);
	public static final Block NETWORK_GATE_DISABLED = new BlockNetworkGate(false);

	public static final Block TRADING_POST = new BlockTradingPost();
	public static final Block MARKET = new BlockMarket();

}