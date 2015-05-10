package pl.uz.mercury.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Transient;
import javax.persistence.Version;

import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryOptionEntity;

public class EntityDtoAssigner <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	private final Class <Entity>			entityClass;
	private final AccessorRetriever			accessorRetriever	= new AccessorRetriever();

	private final Assigner <Entity, Dto>	dtoByEntityAssigner	= (entityL, entityField, dtoL, dtoField) -> dtoField
																		.set(dtoL, accessorRetriever
																				.getGetter(entityField)
																				.invoke(entityL));

	private final Assigner <Entity, Dto>	entityByDtoAssigner	= (entityL, entityField, dtoL, dtoField) -> accessorRetriever
																		.getSetter(entityField).invoke(
																				entityL, dtoField.get(dtoL));

	public EntityDtoAssigner(Class <Entity> entityClass)
	{
		this.entityClass = entityClass;
	}

	public void assignDtoByEntity (Dto dto, Entity entity)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
	{
		assign(Arrays.asList(entity), Arrays.asList(dto), dtoByEntityAssigner);
	}

	public void assignEntityByDto (Entity entity, Dto dto) throws IllegalArgumentException,

	IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		assign(Arrays.asList(entity), Arrays.asList(dto), entityByDtoAssigner);
	}

	public List <Dto> getDtosForEntities (List <Entity> entityList, Class <Dto> dtoClass)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException
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
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
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

	private class AccessorRetriever
	{
		Method getSetter (Field field) throws NoSuchMethodException, SecurityException
		{
			return getAccessor(field, Accessor.setter);
		}

		Method getGetter (Field field) throws NoSuchMethodException, SecurityException
		{
			return getAccessor(field, Accessor.getter);
		}

		private Method getAccessor (Field field, Accessor accessor)
				throws NoSuchMethodException, SecurityException
		{
			String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);

			return entityClass.getMethod(accessor.prefix + capitalizedFieldName);
		}

	}

	enum Accessor
	{
		getter("get"), setter("set");

		Accessor(String prefix)
		{
			this.prefix = prefix;
		}

		public String	prefix;
	}

}

@FunctionalInterface
interface Assigner <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	void assign (Entity entity, Field entityField, Dto dto, Field dtoField)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;
}
