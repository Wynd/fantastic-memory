package model;

public enum Type {
	TODOLIST(1),SHOPPINGLIST(2),APPOINTMENTS(3),TOTAKELIST(4);
	
	private int id;

	private Type(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	

}
