package org.kul.ref;

class KulReflectionService{
	
	public static class INSTANCE{
		private static KulReflectionService instance;
		public static KulReflectionService GET(){ if(instance == null) instance = new KulReflectionService(); return instance; }
	}
	
	public void resetFields(Object o){
		
	}
}