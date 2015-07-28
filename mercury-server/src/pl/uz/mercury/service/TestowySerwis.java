package pl.uz.mercury.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

//@Stateless
public class TestowySerwis
{
//	@EJB
	TestowySerwisDwa	testowySerwis2;

	public int get16 ()
	{
//		return 6 + testowySerwis2.get10();
		return 6;
	}
}
