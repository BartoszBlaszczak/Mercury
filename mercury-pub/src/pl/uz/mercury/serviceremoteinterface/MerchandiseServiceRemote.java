package pl.uz.mercury.serviceremoteinterface;

import javax.ejb.Remote;

@Remote
public interface MerchandiseServiceRemote extends MercuryServiceRemote
{
	
	String getResource();

}
