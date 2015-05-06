package pl.uz.mercury.zobaczmy;

import javax.ejb.Local;

import pl.uz.mercury.entity.MercuryOptionEntity;

@Local
public interface ZobaczmyDaoLocal
{
	public <E extends MercuryOptionEntity> Long saveUniversally (E entity);
	
	public Long saveUniversally2 (MercuryOptionEntity entity);
}
