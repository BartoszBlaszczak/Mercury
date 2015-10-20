package pl.uz.mercury.service.common;

import java.util.List;

import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.filtercriteria.SearchCriteria;

public interface MercuryService <Dto extends MercuryOptionDto>
{
	Long save (Dto dto) throws SavingException, ValidationException;
	Dto retrieve (Long id) throws RetrievingException;
	void delete (Long id) throws DeletingException;
	List <Dto> getList (List <SearchCriteria> criteria) throws RetrievingException, ValidationException;
}