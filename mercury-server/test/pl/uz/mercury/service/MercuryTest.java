package pl.uz.mercury.service;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.uz.mercury.dao.MerchandiseDao;
import pl.uz.mercury.dao.MercuryOptionDao;
import pl.uz.mercury.dao.util.EntityDtoAssigner;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.entity.MercuryOptionEntity;
import pl.uz.mercury.exception.EntityDtoAssignException;

public class MercuryTest
{
	EntityDtoAssigner<MercuryOptionEntity, MercuryOptionDto> entityDtoAssigner = new EntityDtoAssigner<>();

	@Test
	public void createEntityInstanceTest() throws InstantiationException, IllegalAccessException, EntityDtoAssignException
	{
		MerchandiseDto merchandiseDto = new MerchandiseDto();
		
		
		merchandiseDto.name = "zax";
		merchandiseDto.merchandiseId = 666L;
		merchandiseDto.transientField = "as";
		
		MercuryOptionDao<Merchandise, MerchandiseDto> dao = new MerchandiseDao();
		Merchandise merchandise = dao.test(merchandiseDto);
		
		System.out.println(merchandise.getIdmerchandise());
		System.out.println(merchandise.getName());
		System.out.println(merchandise.getTransientField());
		
		assertEquals(merchandiseDto.name, merchandise.getName());
		assertEquals(merchandiseDto.merchandiseId, merchandise.getIdmerchandise());
	}
	
	
	@Test
	public void assignDtoByEntityTest() throws EntityDtoAssignException
	{
		Merchandise merchandise = new Merchandise();
		MerchandiseDto merchandiseDto = new MerchandiseDto();
		
		
		merchandise.setName("oko");
		merchandise.setIdmerchandise(1234567890L);
		merchandise.setTransientField("TRANSIENT HEHE");
		
		
		entityDtoAssigner.assignDtoByEntity(merchandiseDto, merchandise);
		
		System.out.println(merchandiseDto.merchandiseId);
		System.out.println(merchandiseDto.name);
		System.out.println(merchandiseDto.transientField);
		
		assertEquals(merchandiseDto.name, merchandise.getName());
		assertEquals(merchandiseDto.merchandiseId, merchandise.getIdmerchandise());
	}
	
	@Test
	public void assignEntityByDtoTest() throws EntityDtoAssignException
	{
		Merchandise merchandise = new Merchandise();
		MerchandiseDto merchandiseDto = new MerchandiseDto();
		
		
		merchandiseDto.name = "maroko";
		merchandiseDto.merchandiseId = 9876543210L;
		merchandiseDto.transientField = "tralalala";
		
		
		entityDtoAssigner.assignEntityByDto(merchandise, merchandiseDto);
		
		System.out.println(merchandise.getIdmerchandise());
		System.out.println(merchandise.getName());
		System.out.println(merchandise.getTransientField());
		
		assertEquals(merchandiseDto.name, merchandise.getName());
		assertEquals(merchandiseDto.merchandiseId, merchandise.getIdmerchandise());
	}
}
