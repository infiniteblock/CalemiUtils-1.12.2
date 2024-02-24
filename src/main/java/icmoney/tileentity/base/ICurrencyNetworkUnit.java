package icmoney.tileentity.base;

import icmoney.util.Location;

public interface ICurrencyNetworkUnit extends INetwork {

	Location getBankLocation();

	void setBankLocation(Location location);
}
