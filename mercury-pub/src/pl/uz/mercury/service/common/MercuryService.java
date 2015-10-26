package pl.uz.mercury.service.common;

import java.util.List;

import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.searchcriteria.SearchCriteria;

public interface MercuryService <Dto extends MercuryOptionDto>
{
	String USER_ROLE = "users";
	String OBSERVER_ROLE = "observers";
	
	Long save (Dto dto) throws SavingException, ValidationException;
	void delete (Long id) throws DeletingException;
	List <Dto> getList (List <SearchCriteria> criteria) throws RetrievingException, ValidationException;
}