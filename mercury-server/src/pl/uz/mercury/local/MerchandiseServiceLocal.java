package pl.uz.mercury.local;

import javax.ejb.Local;

import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;

@Local
public interface MerchandiseServiceLocal extends MercuryServiceLocal, MerchandiseServiceRemote {

}
