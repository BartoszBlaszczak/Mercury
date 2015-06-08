package pl.uz.mercury.service;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.uz.mercury.Properties;
import pl.uz.mercury.util.PropertiesReader;

@RunWith(Arquillian.class)
public class MercuryTest
{
	@EJB
	PropertiesReader	propertiesReader;

	@Deployment
	public static JavaArchive createArchiveAndDeploy ()
	{
		return ShrinkWrap.create(JavaArchive.class).addClasses(PropertiesReader.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void Test ()
	{
		String property = propertiesReader.getProperty(Properties.persister_name);
		assertEquals("mercuryPersister", property);
	}
}
