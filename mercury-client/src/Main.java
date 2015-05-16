import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;

public class Main
{

	public static void main (String[] args) throws Exception
	{
		// SecurityClient secClient = SecurityClientFactory.getSecurityClient();
		// secClient.setSimple("bartek", "haslo");
		// secClient.login();

//		lookup();
		 save();
//		 getList();
		// em();

	}

	private static void lookup () throws NamingException, RetrievingException, SavingException
	{
		MerchandiseServiceRemote service = InitialContext
				.doLookup("ejb:mercury/mercury-server-0.0.1-SNAPSHOT/MerchandiseServiceImpl!pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote");
		System.out.println(service.save(new MerchandiseDto()));
	}

	private static void save () throws NamingException, SavingException, RetrievingException
	{
		MerchandiseServiceRemote service = InitialContext
				.doLookup("ejb:mercury/mercury-server-0.0.1-SNAPSHOT/MerchandiseServiceImpl!pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote");

		MerchandiseDto dto = new MerchandiseDto();
		dto.name = "kot w worku";

		service.save(dto);
	}

	private static void getList () throws NamingException, SavingException, RetrievingException
	{
		MerchandiseServiceRemote service = InitialContext
				.doLookup("ejb:mercury/mercury-server-0.0.1-SNAPSHOT/MerchandiseServiceImpl!pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote");

		for (MerchandiseDto dto : service.getList())
		{
			System.out.println(dto.name);
		}
	}

	private static void em () throws SecurityException, IOException
	{
		Logger logger = Logger.getLogger("MyLogger");
		String fileName = System.getProperty("user.home") + "/MercuryLog.log";
		FileHandler handler = new FileHandler(fileName, true);
		logger.addHandler(handler);
		handler.setFormatter(new SimpleFormatter());
		logger.info("kolo");
	}

}