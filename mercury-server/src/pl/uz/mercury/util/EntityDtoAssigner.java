package pl.uz.mercury.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Transient;
import javax.persistence.Version;

import pl.uz.mercury.annotation.TransientMercuryDtoField;
import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryOptionEntity;
import pl.uz.mercury.exception.EntityDtoAssignException;

public class EntityDtoAssigner <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	private Assigner <Entity, Dto>	dtoByEntityAssigner	= (entityL, entityField, dtoL, dtoField) -> dtoField.set(dtoL,
														entityField.get(entityL));

	private Assigner <Entity, Dto>	entityByDtoAssigner	= (entityL, entityField, dtoL, dtoField) -> entityField.set(
														entityL, dtoField.get(dtoL));

	public void assignDtoByEntity (Dto dto, Entity entity) throws IllegalArgumentException,
			IllegalAccessException
	{
		assign(Arrays.asList(entity), Arrays.asList(dto), dtoByEntityAssigner);
	}

	public void assignEntityByDto (Entity entity, Dto dto) throws IllegalArgumentException,
			IllegalAccessException
	{
		assign(Arrays.asList(entity), Arrays.asList(dto), entityByDtoAssigner);
	}

	public List <Dto> getDtosForEntities (List <Entity> entityList, Class <Dto> dtoClass)
			throws InstantiationException, IllegalAccessException
	{
		List <Dto> dtoList = new ArrayList <Dto>(entityList.size());

		for (@SuppressWarnings("unused")
		Entity entity : entityList)
		{
			dtoList.add(dtoClass.newInstance());
		}

		assign(entityList, dtoList, dtoByEntityAssigner);

		return dtoList;
	}

	private void assign (List <Entity> entityList, List <Dto> dtoList, Assigner <Entity, Dto> assigner)
			throws IllegalArgumentException, IllegalAccessException
	{

		Field[] entityFields = entityList.get(0).getClass().getDeclaredFields();
		Field[] dtoFields = dtoList.get(0).getClass().getDeclaredFields();
		int listSize = entityList.size();

		for (Field entityField : entityFields)
		{
			if (entityField.getAnnotation(Transient.class) == null
					&& entityField.getAnnotation(Version.class) == null)
			{
				for (Field dtoField : dtoFields)
				{
					if (dtoField.getName().equals(entityField.getName())
							&& dtoField.getType().equals(entityField.getType()))
					{
						entityField.setAccessible(true);

						for (int counter = 0; counter < listSize; counter++)
						{
							assigner.assign(entityList.get(counter), entityField, dtoList.get(counter),
									dtoField);
						}
					}
				}
			}
		}
	}
}

@FunctionalInterface
interface Assigner <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	void assign (Entity entity, Field entityField, Dto dto, Field dtoField) throws IllegalArgumentException,
			IllegalAccessException;
}
