package todolist.todomanagement.model;

public class Item {
	
	private int id;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Item(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
	public Item(String description) {
		super();
		this.description = description;
	}
	
	

}
