import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;

import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;

public class Main
{

	public static void main(String[] args) throws Exception
	{
//		SecurityClient secClient = SecurityClientFactory.getSecurityClient();
//		secClient.setSimple("bartek", "haslo");
//		secClient.login();
		

		lookup();
	}

	private static void lookup() throws NamingException
	{
		MerchandiseServiceRemote service = InitialContext
				.doLookup("ejb:mercury/mercury-server/MerchandiseServiceImpl!pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote");
		System.out.println(service.getResource());
	}

}