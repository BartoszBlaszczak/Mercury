package pl.uz.mercury.interceptor;

import java.util.StringJoiner;

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

		StringJoiner parametersNames = new StringJoiner(", ");

		for (Object parameter : context.getParameters())
		{
			parametersNames.add(parameter.toString());
		}

		StringBuilder info = new StringBuilder().append(operatorName).append(" invoked ").append(className).append(".").append(methodName)
				.append("(").append(parametersNames).append(")");

		Logger.info(info);
		return context.proceed();
	}
}
