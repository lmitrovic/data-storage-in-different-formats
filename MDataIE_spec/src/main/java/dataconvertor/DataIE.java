package dataconvertor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import model.Entity;
import model.Order;
import utils.FileUtils;

public abstract class DataIE {

	protected String fileName;
	private List<Entity> entityList;
	private boolean autoincrement;
	private int ID;

	public DataIE() {
		this.entityList = new ArrayList<Entity>();
	}

	/**
	 * Ova metoda pretvara objekte klase Entity u String koji je u nekom od tri formata (JSON, YAML ili CUSTOM).
	 * @param object Kao parametar prima listu objekata klase Entity.
	 * @return Ova metoda vraća taj String.
	 * @throws Exception Može doći do greške ukoliko se konvertovanje ne izvrši uspešno.
	 */
	public abstract String convertObjectToDataFormat(List<Entity> object) throws Exception;


	/**
	 * Ova metoda pretvara String koji je u nekom od tri formata (JSON, YAML ili CUSTOM) u listu objekata klase Entity.
	 * @param dataFormat Kao prvi parametar prima String koji se prevodi u objekte.
	 * @param classOf Kao drugi parametar prima klasu tih objekata.
	 * @return Ova metoda vraća listu objekata klase Entity.
	 * @throws Exception Može doći do greške ukoliko se konvertovanje ne izvrši uspešno.
	 */
	public abstract List<Entity> convertDataFormatToObjects(String dataFormat, Class<?> classOf) throws Exception;
    
	/**
	 * Ova metoda sadržaj fajla pretvara u String.
	 * @return Ova metoda vraća taj String.
	 * @throws IOException Može doći do greške ukoliko se importovanje ne izvrši uspešno.
	 */
	public String importDataFormat() throws IOException{
		String stringFormat = FileUtils.fileToString(fileName);

		return stringFormat;
	}
    
	/**
	 * Ova metoda prosleđeni String upisuje u fajl.
	 * @param dataFormat Parametar je String koji se upisuje u fajl.
	 * @throws IOException Može doći do greške ukoli se exportovanje ne izvrši uspešno.
	 */
	public void exportDataFormat(String dataFormat) throws IOException{
		File file = new File("");
		file = new File(fileName);
		file.setWritable(true);
		
		FileUtils.stringToFile(file, dataFormat);	
	}
    
	/**
	 * Ova metoda dodaje objekat klase Entity.
	 * @param e Kao parametar prima objekat koji se dodaje.
	 */
	public void addEntity(Entity e) {
		if (!(entityList.contains(e))) {
			entityList.add(e);
		}

	}
    
	/**
	 * Ova metoda dodaje objekat klase Entity.
	 * @param name Kao prvi parametar prima ime objekta.
	 * @param id Kao drugi parametar prima ID objekta, ali se taj ID provarava da li već postoji. Ukoliko postoji, ID se dodeljuje automatski.
	 * @param attributes Kao treći parametar prima mapu atributa objekta.
	 */
	public void addEntity(String name, int id, Map<String, Object> attributes) {
		Entity e = new Entity();

		// da li u listi postoji objekat koji sadrzi taj id?
		boolean con = false;
		for (Entity entity : entityList) {
			if (entity.getId() == id) {
				con = true;
				break;
			}
		}
		if (!con) {
			e.setId(id);
			ID = id + 1;
		} else
			e.setId(++ID);
		e.setName(name);
		e.setAttributes(attributes);
		addEntity(e);
	}

	/**
	 * Ova metoda dodaje objekat klase Entity, a ID se dodeljuje automatski.
	 * @param name Kao prvi parametar prima ime objekta.
	 * @param attributes Kao treci parametar prima mapu atributa objekta.
	 */
	public void addEntity(String name, Map<String, Object> attributes) {
		Entity e = new Entity();
		e.setId(++ID);
		e.setName(name);
		e.setAttributes(attributes);
		addEntity(e);
	}

	/**
	 * Ova metoda dodaje objekat klase Entity.
	 * @param name Kao prvi parametar prima ime objekta i tada treba automatski dodeliti ID.
	 */
	public void addEntity(String name) {
		Entity e = new Entity();
		e.setName(name);
		e.setId(++ID);
		addEntity(e);
	}

	/**
	 * Ova metoda dodaje objekat klase Entity.
	 * @param name Kao prvi parametar prima ime objekta.
	 * @param id Kao drugi parametar prima ID objekta, ali se taj ID provarava da li vec postoji. Ukoliko postoji, ID se dodeljuje automatski.
	 */
	public void addEntity(String name, int id) {
		Entity e = new Entity(name, id);
		// da li u listi postoji objekat koji sadrzi taj kljuc?
		boolean con = false;
		for (Entity entity : entityList) {
			if (entity.getId() == id) {
				con = true;
				break;
			}
		}
		if (!con) {
			e.setId(id);
			ID = id + 1;
		} else
			e.setId(++ID);
		addEntity(e);
	}

	/**
	 * Ova metoda dodaje ugnježdeni Entitet nekom Entitetu. 
	 * Ovaj ugnježdeni Entitet se dodaje kao vrednost atributa sa ključem koji se prosleđuje kao drugi parametar.
	 * @param e Ova metoda kao prvi parametar prima Entitet kome se dodeljuje ugnježdeni Entitet.
	 * @param key Ova metoda kao drugi parametar prima vrednost kljuca za ugnježdeni Entitet.
	 * @param name Ova metoda kao treći parametar prima ime ugnježdenog Entiteta.
	 * @param id Ova metoda kao četvrti parametar prima ID ugnježdenog Entiteta za koji se proverava da li već postoji. Ukoliko postoji, ID se dodeljuje automatski.
	 * @param attributes Ova metoda kao peti parametar prima mapu atributa ugnježdenog Entiteta.
	 */
	public void addNestedEntity(Entity e, String key, String name, int id, Map<String, Object> attributes) {

		Entity entity = new Entity();
		int index = 0;
		for (Entity en : entityList) {
			if (en.getId() == e.getId())
				break;
			index++;
		}
		entity.setName(name);
		boolean con = false;
		for (Entity en : entityList) {
			if (en.getId() == id) {
				con = true;
				break;
			}
		}
		if (!con) {
			entity.setId(id);
			ID = id + 1;
		} else
			entity.setId(++ID);
		entity.setAttributes(attributes);
		entityList.get(index).getAttributes().put(key, entity);

	}

	/**
	 * Ova metoda dodaje ugnježdeni Entitet nekom Entitetu. ID ugnježdenog Entiteta se dodeljuje automatski.
	 * Ovaj ugnježdeni Entitet se dodaje kao vrednost atributa sa ključem koji se prosleđuje kao drugi parametar.
	 * @param e Ova metoda kao prvi parametar prima Entitet kome se dodeljuje ugnježdeni Entitet.
	 * @param key Ova metoda kao drugi parametar prima vrednost kljuca za ugnježdeni Entitet.
	 * @param name Ova metoda kao treći parametar prima ime ugnježdenog Entiteta.
	 * @param attributes Ova metoda kao četvrti parametar prima mapu atributa ugnježdenog Entiteta.
	 */
	public void addNestedEntity(Entity e, String key, String name, Map<String, Object> attributes) {

		Entity entity = new Entity();
		int index = 0;
		for (Entity en : entityList) {
			if (en.getId() == e.getId())
				break;
			index++;
		}
		entity.setName(name);
		entity.setId(++ID);
		entity.setAttributes(attributes);
		entityList.get(index).getAttributes().put(key, entity);

	}

	/**
	 * Ova metoda briše prosleđeni Entitet.
	 * @param e Kao parametar prima objekat klase Entity koji treba obrisati.
	 */
	public void delete(Entity e) {
		entityList.remove(e);
		this.ID--;
	}

	/**
	 * Ova metoda briše Entitet sa prosleđenim ID.
	 * @param id Kao parametar prima ID Entiteta koji se briše.
	 */
	public void delete(int id) {
		for (Entity e : entityList) {
			if (e.getId() == id) {
				delete(e);
				break;
			}
		}
	}

	/**
	 * Ova metoda briše sve Entitete sa prosleđenim imenom.
	 * @param name Kao parametar prima ime Entiteta koji se brišu.
	 */
	public void delete(String name) {
		ArrayList toRemove = new ArrayList<Entity>();
		for (Entity e : entityList) {
			if (e.getName().equals(name)) {
				toRemove.add(e);
			}
		}
		this.ID -= toRemove.size();
		this.entityList.removeAll(toRemove);
	}

	/**
	 * Ova metoda ažurira ime i atribute Entitetu čiji je ID jednak prosleđenom ID.
	 * @param id Kao prvi parametar prima ID Entiteta koji se ažurira.
	 * @param name Kao drugi parametar prima novo ime za Entitet.
	 * @param attributes Kao treći parametar prima nove atribute za Entitet.
	 */
	public void update(int id, String name, Map<String, Object> attributes) {
		for (Entity e : entityList) {
			if (e.getId() == id) {
				e.setName(name);
				e.setAttributes(attributes);
				break;
			}
		}
	}

	/**
	 * Ova metoda ažurira ime i atribute prosleđenom Entitetu.
	 * @param e Kao prvi parametar prima Entitet koji se ažurira.
	 * @param name Kao drugi parametar prima novo ime za Entitet.
	 * @param attributes Kao treći parametar prima nove atribute za Entitet.
	 */
	public void update(Entity e, String name, Map<String, Object> attributes) {
		e.setName(name);
		e.setAttributes(attributes);
	}

	/**
	 * Ova metoda filtrira Entitete tako što neki od znakova poređenja kombinuje sa vrednosti ID i tako se dobija uslov, a vraćaju se svi Entiteti koji ispunjavaju taj uslov.
	 * @param id Ova metoda kao prvi parametar prima vrednost za ID koji se kobinuje sa drugim parametrom.
	 * @param rex Ova metoda kao drugi parametar prima neki od znakova poređenja koji se kombinuje sa vrednosti ID i tako se dobija uslov koji mora biti ispunjen.
	 * @return Ova metoda vraća Entitete koji odgovaraju prosleđenom uslovu.
	 */
	public List<Entity> filter(int id, String rex) {
		List<Entity> objects = new ArrayList<Entity>();

		if (rex.equals("=")) {
			for (Entity e : entityList) {
				if (e.getId() == id) {
					objects.add(copyOfEntity(e));
					break;
				}
			}

		} else if (rex.equals(">=")) {
			for (Entity e : entityList) {
				if (e.getId() >= id) {
					objects.add(copyOfEntity(e));
				}
			}

		} else if (rex.equals("<=")) {
			for (Entity e : entityList) {
				if (e.getId() <= id) {
					objects.add(copyOfEntity(e));
				}
			}

		} else if (rex.equals("<")) {
			for (Entity e : entityList) {
				if (e.getId() < id) {
					objects.add(copyOfEntity(e));
				}
			}

		} else if (rex.equals(">")) {
			for (Entity e : entityList) {
				if (e.getId() > id) {
					objects.add(copyOfEntity(e));
				}
			}

		}

		return objects;

	}

	/**
	 * Ova metoda filtrira Entitete tako sto prikazuje sve Entitete cije ime je prosledjeno ime ili cije ime pocinje prosledjenim Stringom.
	 * @param name Kao prvi parametar prima ime ili String na koji treba da pocinju imena svih vracenih Entiteta.
	 * @param startsWith Kao drugi parametar prima Boolean vrednost da li se prosleđeno ime odnosi na celo ime Entiteta ili treba da se vrate Entiteti čije ime počinje prosleđenim Stringom.
	 * @return Vraća listu filtriranih Entiteta.
	 */
	public List<Entity> filter(String name, boolean startsWith) {
		List<Entity> objects = new ArrayList<Entity>();
		if (startsWith) {
			for (Entity e : entityList) {
				if (e.getName().startsWith(name.replace("%", ""))) {
					objects.add(copyOfEntity(e));
				}

			}
		}else {
			for (Entity e : entityList) {
				if (e.getName().equals(name)) {
					objects.add(copyOfEntity(e));
				}

			}
		}
		return objects;

	}

	/**
	 * Ova metoda filtrira Entitete.
	 * @param id Kao prvi parametar prima vrednost ID.
	 * @param rex1 Kao drugi parametar prima neki od znakova poređenja.
	 * @param name Kao treći parametar prima vrednost za ime. Ukoliko se ta vrednost yavršava %, to znači da se traže Entiteti čije ime počinje prosleđenim Stringom.
	 * @param rex2 Kao četvrti parametar prima (AND ili OR)
	 * @param startsWith Kao peti parametar prima boolean vrednost da li se prosledjeno ime odnosi na celo ime ili se traže Entiteti čije koji počinju prosleđenim Stringom.
	 * @return Ova metoda vraća listu filtriranih Entiteta.
	 */
	public List<Entity> filter(int id, String rex1, String name, String rex2, boolean startsWith) {
		List<Entity> objects = new ArrayList<Entity>();
		if (!startsWith) {
			if (rex2.equals("AND")) {

				if (rex1.equals("=")) {
					for (Entity e : entityList) {
						if (e.getId() == id && e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals(">=")) {
					for (Entity e : entityList) {
						if (e.getId() >= id && e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<=")) {
					for (Entity e : entityList) {
						if (e.getId() <= id && e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<")) {
					for (Entity e : entityList) {
						if (e.getId() < id && e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals(">")) {
					for (Entity e : entityList) {
						if (e.getId() > id && e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				}
			} else {

				if (rex1.equals("=")) {
					for (Entity e : entityList) {
						if (e.getId() == id || e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals(">=")) {
					for (Entity e : entityList) {
						if (e.getId() >= id || e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<=")) {
					for (Entity e : entityList) {
						if (e.getId() <= id || e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<")) {
					for (Entity e : entityList) {
						if (e.getId() < id || e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals(">")) {
					for (Entity e : entityList) {
						if (e.getId() > id && e.getName().equals(name)) {
							objects.add(copyOfEntity(e));
						}
					}

				}
			}
		} else {
			if (rex2.equals("AND")) {

				if (rex1.equals("=")) {
					for (Entity e : entityList) {
						if (e.getId() == id && e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
							break;
						}
					}

				} else if (rex1.equals(">=")) {
					for (Entity e : entityList) {
						if (e.getId() >= id && e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<=")) {
					for (Entity e : entityList) {
						if (e.getId() <= id && e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<")) {
					for (Entity e : entityList) {
						if (e.getId() < id && e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals(">")) {
					for (Entity e : entityList) {
						if (e.getId() > id && e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				}
			} else {

				if (rex1.equals("=")) {
					for (Entity e : entityList) {
						if (e.getId() == id || e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals(">=")) {
					for (Entity e : entityList) {
						if (e.getId() >= id || e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<=")) {
					for (Entity e : entityList) {
						if (e.getId() <= id || e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals("<")) {
					for (Entity e : entityList) {
						if (e.getId() < id || e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				} else if (rex1.equals(">")) {
					for (Entity e : entityList) {
						if (e.getId() > id && e.getName().startsWith(name.replace("%", ""))) {
							objects.add(copyOfEntity(e));
						}
					}

				}
			}
		}

		return objects;

	}
	
	private Entity copyOfEntity(Entity e) {
		Entity entity = new Entity();
		entity.setId(e.getId());
		entity.setName(e.getName());
		entity.setAttributes(e.getAttributes());
		return entity;
	}

	/**
	 * Ova metoda sortira Entitete.
	 * @param type Kao parametar prima kako će se Entiteti sortirati: rastuće po ID, opadajuće po ID, rastuće po imenu ili opadajuće po imenu.
	 * @return Ova metoda vraća listu sortiranih Entiteta.
	 */
	public List<Entity> sort(Order type) {
		List<Entity> ret = entityList;

		if (type.equals(Order.nameAsc)) {
			Collections.sort(ret, Entity.sortNameAsc);
		} else if (type.equals(Order.nameDesc)) {
			Collections.sort(ret, Entity.sortNameDesc);
		} else if (type.equals(Order.idAsc)) {
			Collections.sort(ret, Entity.sortIdAsc);
		} else if (type.equals(Order.idDesc)) {
			Collections.sort(ret, Entity.sortIdDesc);
		}

		return ret;

	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public void setAutoincrement(boolean autoincrement) {
		this.autoincrement = autoincrement;
	}

	public boolean isAutoincrement() {
		return autoincrement;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getID() {
		return ID;
	}

}
