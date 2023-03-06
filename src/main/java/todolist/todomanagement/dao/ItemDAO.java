package todolist.todomanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import todolist.todomanagement.model.Item;

public class ItemDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/todolist?characterEncoding=latin1&useUnicode=true&character_set_server=utf8mb4&useLegacyDatetimeCode=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "AdminRoot";
	
	private static final String INSERT_ITEMS_SQL = "INSERT INTO items" + " (description) VALUES " + " (?);";
	private static final String SELECT_ALL_ITEMS = " select * from items";
	private static final String SELECT_ITEM_BY_ID = " select * from items where id = ?;";
	private static final String DELETE_ITEM_SQL = "delete from items where id = ?;";
	private static final String Update_ITEM_SQL = "UPDATE items SET description = ? where id = ?;";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
		
	}
	//Create or insert to do item
	public void insertItem(Item item) throws SQLException{
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEMS_SQL)){
			preparedStatement.setString(1, item.getDescription());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//update todo item
	public boolean updateItem(Item item) throws SQLException{
		boolean rowUpdated;
		try(Connection connection = getConnection(); 
				PreparedStatement statement = connection.prepareStatement(Update_ITEM_SQL)){
			
			statement.setInt(2, item.getId());
			statement.setString(1, item.getDescription());
			
			statement.executeUpdate();
			
			rowUpdated = statement.executeUpdate() > 0;
			
		}
		return rowUpdated;
		
	}
	//select item by id
	public Item selectItem(int id){
		Item item = null;
		//Step1 Establishing a Connection
		try(Connection connection = getConnection(); 
				//Step2: Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_ID)){
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			//Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String description = rs.getString("description");
				item = new Item(id, description);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	//Select all Items
	public List<Item> selectAllItem(){
		List<Item> items = new ArrayList<>();
		//Step1 Establishing a Connection
		try(Connection connection = getConnection(); 
				//Step2: Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS)){
			System.out.println(preparedStatement);
			//Step 3: Execute the query or update query
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String description = rs.getString("description");
				items.add(new Item(id, description));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	//Delete To do item
		public boolean deleteItem(int id) throws SQLException{
			boolean rowDeleted;
			try(Connection connection = getConnection(); 
					PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_SQL)){
				statement.setInt(1, id);
				
				statement.executeUpdate();
				
				rowDeleted = statement.executeUpdate() > 0;
				
			}
			return rowDeleted;
			
		}

}
