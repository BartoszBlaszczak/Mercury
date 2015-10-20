package pl.uz.mercury.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.ejb3.annotation.SecurityDomain;

import pl.uz.mercury.Properties;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.filtercriteria.SearchCriteria;
import pl.uz.mercury.interceptor.LoggingInterceptor;
import pl.uz.mercury.service.MerchandiseService;
import pl.uz.mercury.service.common.MercuryServiceImpl;

@Stateless
@Remote(MerchandiseService.class)
public class MerchandiseServiceImpl
	extends MercuryServiceImpl <Merchandise, MerchandiseDto>
	implements MerchandiseService
{
	@Resource 
	private SessionContext context;
	
	public MerchandiseServiceImpl()
	{
		super(Merchandise.class, MerchandiseDto.class);
	}
	
	@Override
	protected void validate (MerchandiseDto dto)
			throws ValidationException
	{
		if (dto.name == null || dto.name.isEmpty())
			throw new ValidationException(MerchandiseDto.NAME);
	}
}
