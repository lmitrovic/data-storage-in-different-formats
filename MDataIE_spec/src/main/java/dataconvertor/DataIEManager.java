package dataconvertor;

public class DataIEManager {
	
	private static DataIE instance;
	
	
	public static void registerDataIE(DataIE manager) {
		instance = manager;
	}
	
	public static DataIE getDataIE(String fileName) {
		instance.setFileName(fileName);
		return instance;
	}

}
