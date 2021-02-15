package model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Entity {
	
	private String name;
	private int id;
	private Map<String, Object> attributes;
	
	
	
	public Entity(String name, int id) {
		this.name = name;
		this.id = id;
		this.attributes = new HashMap<String, Object>();
	}
	
	public Entity() {
		
	}
	
	public static Comparator<Entity> sortNameAsc = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity e1, Entity e2) {
			String name1 = e1.getName();
			String name2 = e2.getName();
			return name1.compareTo(name2);
		}
	};

	public static Comparator<Entity> sortNameDesc = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity e1, Entity e2) {
			String name1 = e1.getName();
			String name2 = e2.getName();
			return name2.compareTo(name1);
		}
	};
	
	public static Comparator<Entity> sortIdAsc = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity e1, Entity e2) {
			int id1 = e1.getId();
			int id2 = e2.getId();
			return ((Integer)id1).compareTo((Integer)id2);
		}
	};

	public static Comparator<Entity> sortIdDesc = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity e1, Entity e2) {
			int id1 = e1.getId();
			int id2 = e2.getId();
			return ((Integer)id2).compareTo((Integer)id1);
		}
	};
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Map<String, Object> getAttributes() {
		return attributes;
	}


	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}


	@Override
	public String toString() {
		return "Entity [name=" + name + ", id=" + id + ", attributes=" + attributes + "]";
	}
	
	
	
	

}
