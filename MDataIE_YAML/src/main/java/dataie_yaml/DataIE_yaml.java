package dataie_yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import dataconvertor.DataIE;
import dataconvertor.DataIEManager;
import model.Entity;
import utils.FileUtils;

public class DataIE_yaml extends DataIE{
	
	static {
		DataIEManager.registerDataIE(new DataIE_yaml());
	}
	
	
	public DataIE_yaml() {
		super();
	}

	@Override
	public List<Entity> convertDataFormatToObjects(String yaml, Class<?> classOf) throws Exception {
		
		List<Entity> ret = new ArrayList<Entity>();
	    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
	    
		for (String s : yaml.split("\n---\n")) {
			Entity e = (Entity) mapper.readValue(s, classOf);
			
			Iterator iterator = e.getAttributes().entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry mapElement = (Map.Entry) iterator.next();
				if (mapElement.getValue() instanceof String) {
					
				}else if (mapElement.getValue() instanceof Number) {
					
				}else {
					Entity ugnjezdeni = new Entity();
					Map map = new HashMap<String, Object>();
					String entity = mapElement.getValue().toString();
					StringBuilder builder = new StringBuilder();
					builder.append(entity.replace(" ", ""));
					builder.deleteCharAt(0);
					builder.deleteCharAt(builder.length()-1);
					int br = 0;
					for (String str : builder.toString().split(",")) {
						String f[] = str.split("=");
						if (br==0) ugnjezdeni.setName(f[1]);
						else if (br==1) {
							if (f[1].contains(".")) ugnjezdeni.setId((int)Math.round(Double.parseDouble(f[1])));
							else ugnjezdeni.setId(Integer.parseInt(f[1]));
						}
						else if (br==2) {
							if (isNumeric(f[2]))
							    map.put(f[1].replace("{", ""),Double.parseDouble(f[2]));
							else
								map.put(f[1].replace("{", ""),f[2]);
						}else {
							String f1 = f[1].replace("}", "");
							if (isNumeric(f1))
							   map.put(f[0], Double.parseDouble(f1));
							else map.put(f[0], f1);
						}
						br++;
					}
					ugnjezdeni.setAttributes(map);
					mapElement.setValue(ugnjezdeni);

				}
			}
			ret.add(e);
		}
		super.setEntityList(ret);
		return ret;
	}

	@Override
	public String convertObjectToDataFormat(List<Entity> objects) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		for(Object o : objects) {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            String yaml = mapper.writeValueAsString(o);
            sb.append(yaml);
			
		}
		
		return sb.toString();
	}

	
	private boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}

}
