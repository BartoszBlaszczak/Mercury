package pl.uz.mercury.serviceremoteinterface.common;

import java.util.List;

import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.UpdatingException;

public interface MercuryCrudOptionServiceInterface <Dto extends MercuryOptionDto>
	extends MercuryService
{
	Long save (Dto dto) throws SavingException;

	Dto retrieve (Long id) throws RetrievingException;

	void update (Dto dto) throws UpdatingException;

	void delete (Long id) throws DeletingException;

	List <Dto> getList () throws RetrievingException;

}
