package pl.uz.mercury.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.Transient;
import javax.persistence.Version;

import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryEntity;
import pl.uz.mercury.exception.EntityDtoCopyException;

public class EntityDtoCopier <Entity extends MercuryEntity, Dto extends MercuryOptionDto>
{
	private final Class <Entity>			entityClass;
	private final AccessorRetriever			accessorRetriever	= new AccessorRetriever();

	private final Assigner <Entity, Dto>	dtoByEntityAssigner	= (entityL, entityField, dtoL, dtoField) -> dtoField.set(dtoL, accessorRetriever
																		.getEntityGetter(entityField).invoke(entityL));

	private final Assigner <Entity, Dto>	entityByDtoAssigner	= (entityL, entityField, dtoL, dtoField) -> accessorRetriever.getEntitySetter(
																		entityField).invoke(entityL, dtoField.get(dtoL));

	public EntityDtoCopier(Class <Entity> entityClass)
	{
		this.entityClass = entityClass;
	}

	public Dto getDtoForEntity (Entity entity, Class <Dto> dtoClass)
			throws EntityDtoCopyException
	{
		try
		{
			Dto dto = dtoClass.newInstance();
			assign(Arrays.asList(entity), Arrays.asList(dto), dtoByEntityAssigner);
			return dto;
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			throw new EntityDtoCopyException();
		}
	}

	public void fillEntityByDto (Entity entity, Dto dto)
			throws EntityDtoCopyException
	{
		try
		{
			assign(Arrays.asList(entity), Arrays.asList(dto), entityByDtoAssigner);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			throw new EntityDtoCopyException();
		}
	}

	public List <Dto> getDtosForEntities (List <Entity> entityList, Class <Dto> dtoClass)
			throws EntityDtoCopyException
	{
		try
		{
			if (entityList.isEmpty()) return new ArrayList <Dto>(0);
			List <Dto> dtoList = new ArrayList <Dto>(entityList.size());

			for (@SuppressWarnings("unused")
			Entity entity : entityList)
				dtoList.add(dtoClass.newInstance());

			assign(entityList, dtoList, dtoByEntityAssigner);

			return dtoList;
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			throw new EntityDtoCopyException();
		}
	}

	private void assign (List <Entity> entityList, List <Dto> dtoList, Assigner <Entity, Dto> assigner)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Field[] entityFields = getEntityFieldsAccesible(entityList.get(0).getClass());
		Field[] dtoFields = dtoList.get(0).getClass().getDeclaredFields();
		int listSize = entityList.size();

		for (Field entityField : entityFields)
		{
			if (entityField.getAnnotation(Transient.class) == null && entityField.getAnnotation(Version.class) == null)
			{
				for (Field dtoField : dtoFields)
				{
					if (dtoField.getName().equals(entityField.getName()) && dtoField.getType().equals(entityField.getType()))
					{
						entityField.setAccessible(true);

						for (int counter = 0; counter < listSize; counter++)
						{
							assigner.assign(entityList.get(counter), entityField, dtoList.get(counter), dtoField);
						}
					}
				}
			}
		}
	}

	private Field[] getEntityFieldsAccesible (Class <?> entityClass, Field... fields)
	{
		Field[] declaredFields = entityClass.getDeclaredFields();
		for (Field field : declaredFields)
			field.setAccessible(true);
		Class <?> superclass = entityClass.getSuperclass();
		if (superclass.equals(Object.class))
			return Stream.concat(Arrays.stream(fields), Arrays.stream(declaredFields)).toArray(Field[]::new);
		else return getEntityFieldsAccesible(superclass, declaredFields);
	}

	private class AccessorRetriever
	{
		Method getEntitySetter (Field field)
				throws NoSuchMethodException, SecurityException
		{
			String methodName = getAccessorName(field, AccessorPrefix.setter);
			return getAccessor(methodName);
		}

		Method getEntityGetter (Field field)
				throws NoSuchMethodException, SecurityException
		{
			String methodName = getAccessorName(field, AccessorPrefix.getter);
			return entityClass.getMethod(methodName);
		}

		private String getAccessorName (Field field, AccessorPrefix accessorPrefix)
		{
			String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			return accessorPrefix.prefix + capitalizedFieldName;
		}

		private Method getAccessor (String methodName)
				throws NoSuchMethodException
		{
			for (Method method : entityClass.getMethods())
			{
				if (method.getName().equals(methodName)) { return method; }
			}

			throw new NoSuchMethodException();
		}
	}
}

enum AccessorPrefix
{
	getter("get"),
	setter("set");

	AccessorPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public String	prefix;
}

@FunctionalInterface
interface Assigner <Entity extends MercuryEntity, Dto extends MercuryOptionDto>
{
	void assign (Entity entity, Field entityField, Dto dto, Field dtoField)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
