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

import pl.uz.mercury.service.TestService;

@RunWith(Arquillian.class)
public class MercuryTest
{
	@EJB
	TestService	service;

	@Deployment
	public static JavaArchive createArchiveAndDeploy ()
	{
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(TestService.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void Test ()
	{
		int resource = service.getSix();
		assertEquals(6, resource);
	}
}
