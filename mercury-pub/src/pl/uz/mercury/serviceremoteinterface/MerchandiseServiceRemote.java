package pl.uz.mercury.serviceremoteinterface;

import javax.ejb.Remote;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.serviceremoteinterface.common.MercuryCrudOptionServiceInterface;

@Remote
public interface MerchandiseServiceRemote
	extends MercuryCrudOptionServiceInterface <MerchandiseDto>
{

	String getResource ();
}
