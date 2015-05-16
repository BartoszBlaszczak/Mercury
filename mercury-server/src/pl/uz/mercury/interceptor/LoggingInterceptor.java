package pl.uz.mercury.interceptor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

//import org.apache.log4j.Logger;



public class LoggingInterceptor
{
	
	@Resource 
	SessionContext ctx;

	@AroundInvoke
	public Object audit (InvocationContext context) throws Exception
	{
		String logRecord = context.getMethod().getName();
		String user = ctx.getCallerPrincipal().getName();
		
		System.out.println(logRecord + " " + user);
		
		
//		Logger logger = Logger.getLogger(context.getMethod().getDeclaringClass());
//		
//		
//		Class[] genericParameterClasses = context.getMethod().getParameterTypes();
//		
//		StringBuilder stringBuilder = new StringBuilder();
//		
//		for (Class<?> clazz : genericParameterClasses)
//		{
//			stringBuilder.append(" ").append(clazz.getSimpleName());
//		}
//		
//		
//		
//		logger.info("AAA " + context.getMethod().getName() + stringBuilder.toString());
		
		
		
		
		
		return context.proceed();
	}
}




















