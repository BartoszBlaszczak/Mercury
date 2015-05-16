package pl.uz.mercury.interceptor;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.pmw.tinylog.Logger;

public class LoggingInterceptor
{

	@Resource
	SessionContext	sessionContext;

	@AroundInvoke
	public Object log (InvocationContext context) throws Exception
	{
		String operatorName = sessionContext.getCallerPrincipal().getName();
		String className = context.getMethod().getDeclaringClass().getSimpleName();
		String methodName = context.getMethod().getName();

		String space = " ";

		Object[] parameters = context.getParameters();
		StringBuilder parametersNames = new StringBuilder();

		for (Object parameter : parameters)
		{
			parametersNames.append(space).append(parameter.toString());
		}

		StringBuilder info = new StringBuilder().append(operatorName).append(space).append(methodName).append(parametersNames).append(space)
				.append("by ").append(className);

		Logger.info(info);

		return context.proceed();
	}
}
