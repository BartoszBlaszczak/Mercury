package pl.uz.mercury.serviceremoteinterface;

import javax.ejb.Remote;

@Remote
public interface MerchandiseServiceRemote
{

	String getResource();

}
