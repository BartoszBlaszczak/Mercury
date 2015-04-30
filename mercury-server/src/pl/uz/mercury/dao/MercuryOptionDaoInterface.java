package pl.uz.mercury.dao;

import javax.ejb.Local;

import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.MercuryOptionEntity;
import pl.uz.mercury.exception.SavingException;

public interface MercuryOptionDaoInterface <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	public Long save(Dto dto) throws SavingException;
}
