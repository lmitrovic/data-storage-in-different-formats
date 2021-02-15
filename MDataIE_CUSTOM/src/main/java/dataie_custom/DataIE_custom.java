package dataie_custom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dataconvertor.DataIE;
import dataconvertor.DataIEManager;
import model.Entity;
import utils.FileUtils;

public class DataIE_custom extends DataIE {

	static {
		DataIEManager.registerDataIE(new DataIE_custom());
	}

	public DataIE_custom() {
		super();
	}

	@Override
	public List<Entity> convertDataFormatToObjects(String custom, Class<?> classOf) throws Exception {
		List<Entity> list = new ArrayList<Entity>();
		for (String entity : custom.split("\n")) {
			Entity e = readEntityFromString(entity);
			list.add(e);
		}
		super.setEntityList(list);
		return list;
	}

	private Entity readEntityFromString(String entity) {

		Entity e = new Entity();
		Map mapa = new HashMap<String, Object>();
		int counter = 0;
		for (String field : entity.split(";")) {
			if (field.contains("={")) {
				String s[] = field.split("=", 2);
				String x = s[1].replace("{", "");
				String p = x.replace("}", "");
				Entity u = new Entity();
				Map mapaU = new HashMap<String, Object>();
				int br = 0;
				for (String r : p.split(",")) {
					String m[] = r.split("=");
					if (br == 0)
						u.setName(m[1].replace("'", ""));
					else if (br == 1)
						u.setId(Integer.parseInt(m[1]));
					else if (m[1].startsWith("'")) {
						mapaU.put(m[0], m[1].replace("'", ""));
					} else {
						mapaU.put(m[0], Integer.parseInt(m[1]));
					}
					br++;
				}
				u.setAttributes(mapaU);
				mapa.put(s[0], u);

			} else {

				String str[] = field.split("=");
				if (counter == 0) {
					e.setName(str[1].replace("'", ""));
				} else if (counter == 1) {
					e.setId(Integer.parseInt(str[1]));
				} else if (str[1].startsWith("'")) {
					mapa.put(str[0], str[1].replace("'", ""));
				} else {
					mapa.put(str[0], Integer.parseInt(str[1]));
				}
				counter++;

			}

		}

		e.setAttributes(mapa);

		return e;

	}

	@Override
	public String convertObjectToDataFormat(List<Entity> objects) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Entity e : objects) {
			sb.append("name=");
			sb.append("'" + e.getName() + "'" + ";");
			sb.append("id=");
			sb.append(e.getId());

			Iterator iterator = e.getAttributes().entrySet().iterator();

			while (iterator.hasNext()) {

				Map.Entry mapElement = (Map.Entry) iterator.next();
				sb.append(';');
				sb.append(mapElement.getKey());
				sb.append('=');
				if (mapElement.getValue() instanceof Entity) {
					Entity entity = (Entity) mapElement.getValue();

					sb.append('{');
					sb.append("name=");
					sb.append("'" + entity.getName() + "'" + ",");
					sb.append("id=");
					sb.append(entity.getId());

					Iterator eIterator = entity.getAttributes().entrySet().iterator();

					while (eIterator.hasNext()) {
						Map.Entry element = (Map.Entry) eIterator.next();
						sb.append(',');
						sb.append(element.getKey());
						sb.append('=');
						if (element.getValue() instanceof String) {
							sb.append("'");
							sb.append(element.getValue());
							sb.append("'");
						} else
							sb.append(element.getValue());

					}
					sb.append('}');

				} else if (mapElement.getValue() instanceof String) {
					sb.append("'");
					sb.append(mapElement.getValue());
					sb.append("'");
				} else
					sb.append(mapElement.getValue());

			}
			sb.append('\n');

		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

}
