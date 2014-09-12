package org.kul.inst;

import java.lang.instrument.Instrumentation;
import java.util.HashSet;

import org.kul.inst.err.KulInstrumentationNotInstantiatedException;


//requires java -javaagent:main.jar Main
//View pom of app project for manifest/premium class info

public class KulInstrumentationService{

	//private static final Logger LOGGER = Logger.getLogger(KulInstrumentationService.class);

	public static void premain(String args, Instrumentation inst) {
		KulInstrumentationService.INSTANCE.instantiate(inst);
	}

	private Instrumentation instrumentation;

	KulInstrumentationService(Instrumentation inst) {
		this.instrumentation = inst;	
	}

	public static class INSTANCE{
		protected static void instantiate(Instrumentation inst){ instance = new KulInstrumentationService(inst);}
		private static KulInstrumentationService instance;
		public static KulInstrumentationService GET() throws KulInstrumentationNotInstantiatedException{ 
			if(instance == null) 
				throw new KulInstrumentationNotInstantiatedException("Java agent not instantiated");
			return instance;
		}
	}

	public Long getObjectSize(final Object o){
		return instrumentation.getObjectSize(o);
	}

	/**
	 * Returns complete object memory allocation for object and all fields
	 * Ignores static fields - use getObjectSize instead
	 */	
	public <T extends Object> Long getDeepObjectSize(final T t){		
		return getDeepObjectSizeRecursive(t, new HashSet<Integer>());
	}

	private <T extends Object> Long getDeepObjectSizeRecursive(final T t, HashSet<Integer> hashes){
		Long bytes = new Long(0);

		HashSet<java.lang.reflect.Field> fields = new HashSet<java.lang.reflect.Field>();
		for(Class<?> c = t.getClass(); !c.isAssignableFrom(Object.class) ; c = c.getSuperclass())
			fields.addAll(java.util.Arrays.asList(c.getDeclaredFields()));
		
		try {
			for(java.lang.reflect.Field field : new HashSet<java.lang.reflect.Field>(fields)){
				boolean access = field.isAccessible();
				if(!access)
					field.setAccessible(true);
				if(field.get(t) == null || hashes.contains(field.get(t).hashCode())
						||  java.lang.reflect.Modifier.isStatic(field.getModifiers()))					
					fields.remove(field);			
				if(!access) 
					field.setAccessible(false);
			}

			for(java.lang.reflect.Field field : fields){
				boolean access = field.isAccessible();
				if(!access)
					field.setAccessible(true);
				hashes.add(field.get(t).hashCode());
				if(field.getType().isPrimitive())
					bytes += getObjectSize(field.get(t));
				else
					bytes += getDeepObjectSizeRecursive(field.get(t), hashes);
				if(!access) field.setAccessible(false);
			}				
		}
		catch (IllegalArgumentException e) 	{ e.printStackTrace(); }
		catch (IllegalAccessException e) 		{ e.printStackTrace(); }

		return bytes;
	}
}