package icmoney.packet;

import icmoney.ICMoney;
import icmoney.tileentity.TileEntityBank;
import icmoney.tileentity.base.TileEntityBase;
import icmoney.util.Location;
import icmoney.util.helper.ItemHelper;
import icmoney.util.helper.PacketHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerPacketHandler implements IMessage {

	String text;

	public ServerPacketHandler() {

	}

	public ServerPacketHandler(String text) {

		this.text = text;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		text = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		ByteBufUtils.writeUTF8String(buf, text);
	}

	public static class Handler implements IMessageHandler<ServerPacketHandler, IMessage> {

		@Override
		public IMessage onMessage(ServerPacketHandler message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(ServerPacketHandler message, MessageContext ctx) {
			String[] data = message.text.split("%");
			EntityPlayer player = ctx.getServerHandler().player;
			World world = ctx.getServerHandler().player.getServerWorld();

			if (data[0].equalsIgnoreCase("te-enable")) {

				Location location = PacketHelper.getLocation(world, data, 1);
				boolean value = Boolean.valueOf(data[4]);

				TileEntity te = location.getTileEntity();

				if (te instanceof TileEntityBase) {
					((TileEntityBase) te).enable = value;
					((TileEntityBase) te).markForUpdate();
				}
			}

			else if (data[0].equalsIgnoreCase("gui-open")) {

				int id = Integer.parseInt(data[1]);

				player.openGui(ICMoney.instance, id, player.world, (int) player.posX, (int) player.posY,
						(int) player.posZ);
			}

			else if (data[0].equalsIgnoreCase("bank-sync")) {

				Location location = PacketHelper.getLocation(world, data, 3);

				TileEntityBank tileEntity = (TileEntityBank) location.getTileEntity();

				if (tileEntity != null) {
					NBTTagCompound nbt = ItemHelper.getNBT(tileEntity.getStackInSlot(1));

					tileEntity.storedCurrency = Integer.parseInt(data[1]);
					nbt.setInteger("balance", Integer.parseInt(data[2]));
				}
			}
		}
	}
}