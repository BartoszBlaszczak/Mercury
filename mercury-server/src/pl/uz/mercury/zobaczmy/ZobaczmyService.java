package pl.uz.mercury.zobaczmy;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.entity.Operator;

@Stateless
public class ZobaczmyService implements ZobaczmyServiceLocal
{
	@EJB
	ZobaczmyDaoLocal dao;

	@Override
	public Long em ()
	{
		Merchandise encja = new Merchandise();
		encja.setName("nazwa");
		
		Operator o = new Operator();
		o.setLogin("o");
		o.setPassword("ooo");
		
		dao.saveUniversally2(o);
		
		
		return dao.saveUniversally2(encja);
	}
	
}
