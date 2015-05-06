package pl.uz.mercury.util;

import java.lang.reflect.ParameterizedType;

import pl.uz.mercury.dao.common.MercuryOptionDao;

public class GenericClassRetriver
{

	@SuppressWarnings("rawtypes")
	public Class getGenericClassObject(Class clazz, final int GENERIC_TYPE_ORDER)
	{
		Class genericSource = findGenericSourceClass (clazz);
		Class classObject = (Class) ((ParameterizedType) genericSource.getGenericSuperclass ())
				.getActualTypeArguments ()[GENERIC_TYPE_ORDER];

		return classObject;
	}

	@SuppressWarnings({ "rawtypes" })
	private Class findGenericSourceClass(final Class clazz)
	{
		Class superclass = clazz.getSuperclass ();
		if (superclass.equals (MercuryOptionDao.class)) { return clazz; }
		return findGenericSourceClass (superclass);

	}
}
