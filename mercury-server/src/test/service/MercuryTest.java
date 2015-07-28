package test.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.service.TestowySerwis;
import pl.uz.mercury.service.TestowySerwisDwa;

@RunWith(Arquillian.class)
public class MercuryTest
{
	@Inject
//	@EJB
	TestowySerwis	serwis;

	@Deployment
	public static JavaArchive createArchiveAndDeploy ()
	{
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(TestowySerwis.class, TestowySerwisDwa.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void test () throws SavingException, ValidationException
	{
		assertEquals(16, serwis.get16());
	}
}
