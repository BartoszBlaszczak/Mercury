package pl.uz.mercury.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.persistence.Version;

import pl.uz.mercury.annotation.TransientMercuryDtoField;
import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.MercuryOptionEntity;
import pl.uz.mercury.exception.EntityDtoAssignException;

public class EntityDtoAssigner <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	public void assignDtoByEntity(Dto dto, Entity entity) throws EntityDtoAssignException
	{
		assignEntityOrDto(entity, dto, (entityL, entityField, dtoL, dtoField) -> dtoField.set(dto, entityField.get(entity)));
	}

	public void assignEntityByDto(Entity entity, Dto dto) throws EntityDtoAssignException
	{
		assignEntityOrDto(entity, dto, (entityL, entityField, dtoL, dtoField) -> entityField.set(entityL, dtoField.get(dto)));
	}

	private void assignEntityOrDto(Entity entity, Dto dto, Assigner<Entity, Dto> assigner) throws EntityDtoAssignException
	{

		if ((dto == null) || (entity == null)) { throw new NullPointerException("Dto or entity is null"); }

		Field[] dtoFields = dto.getClass().getDeclaredFields();
		Field[] entityFields = entity.getClass().getDeclaredFields();

		for (Field dtoField : dtoFields)
		{
			if (dtoField.getAnnotation(TransientMercuryDtoField.class) == null && dtoField.getAnnotation(Version.class) == null)
			{
				for (Field entityField : entityFields)
				{
					if (entityField.getAnnotation(Transient.class) == null && entityField.getAnnotation(Version.class) == null)
					{
						if (dtoField.getName().equals(entityField.getName()) && dtoField.getType().equals(entityField.getType()))
						{
							try
							{
								entityField.setAccessible(true);
								assigner.assign(entity, entityField, dto, dtoField);
							}
							catch (IllegalArgumentException | SecurityException | IllegalAccessException e)
							{
								throw new EntityDtoAssignException();
							}
							continue;
						}
					}
				}
			}
		}
	}
	
	public List<Dto> getDtosForEntities (List<Entity> entityList, Class<Dto> dtoClass)
	{
		if (entityList.isEmpty())
		{
			return new ArrayList<Dto>();
		}
		
		int listSize = entityList.size();
		
		List<Dto> dtoList = new ArrayList<Dto>(entityList.size());
		
		for (Entity entity : entityList)
		{
			dtoList.add(dtoClass.newInstance());
		}
			
		
		Field[] dtoFields = dtoClass.getDeclaredFields();
		Field[] entityFields = entityList.get(0).getClass().getDeclaredFields();
		
		
		for (Field dtoField : dtoFields)
		{
			if (dtoField.getAnnotation(TransientMercuryDtoField.class) == null && dtoField.getAnnotation(Version.class) == null)
			{
				for (Field entityField : entityFields)
				{
					if (entityField.getAnnotation(Transient.class) == null && entityField.getAnnotation(Version.class) == null)
					{
						if (dtoField.getName().equals(entityField.getName()) && dtoField.getType().equals(entityField.getType()))
						{
							try
							{
								entityField.setAccessible(true);
								
								
								for (int counter = 0; counter < listSize; counter++)
								{
									assigner.assign(entity, entityField, dto, dtoField);
									//dtoField.set(dtoList.get(counter), entityField.get(entityList.get(counter)));
								} 
							}
							catch (IllegalArgumentException | SecurityException | IllegalAccessException e)
							{
								throw new EntityDtoAssignException();
							}
							continue;
						}
					}
				}
			}
		}
		
		
		
		
		
		
		
		return dtoList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

@FunctionalInterface
interface Assigner<Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	void assign(Entity entity, Field entityField, Dto dto, Field dtoField) throws IllegalArgumentException, IllegalAccessException;
}

