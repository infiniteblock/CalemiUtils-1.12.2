package icmoney.tileentity;

import icmoney.security.ISecurity;
import icmoney.security.SecurityProfile;
import icmoney.tileentity.base.INetwork;
import icmoney.tileentity.base.TileEntityBase;
import net.minecraft.util.EnumFacing;

public class TileEntityNetworkCable extends TileEntityBase implements INetwork, ISecurity {

	private final SecurityProfile profile = new SecurityProfile();

	@Override
	public void update() {

	}

	@Override
	public SecurityProfile getSecurityProfile() {

		return profile;
	}

	@Override
	public EnumFacing[] getConnectedDirections() {

		return EnumFacing.VALUES;
	}
}
