import javax.naming.InitialContext;
import javax.naming.NamingException;

import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceImplRemote;

public class Main
{

	public static void main(String[] args) throws NamingException
	{
		 lookup();
	}

	private static void lookup() throws NamingException
	{
		MerchandiseServiceImplRemote service = InitialContext
				.doLookup("ejb:mercury/mercury-server/MerchandiseServiceImpl!pl.uz.mercury.serviceremoteinterface.MerchandiseServiceImplRemote");
		System.out.println(service.getResource());
	}

}