package model.dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.IUserDAO;
import model.user.User;

public class DBUserDAO implements IUserDAO {

	private List<User> allUsers;
	
	public DBUserDAO(){
		this.allUsers = this.getAllUsers();
	}
	
	
	@Override
	public void addUser(User user) {
		PreparedStatement st = null;
		try {
			st = DBManager.getInstance().getConnection().prepareStatement("INSERT INTO " + DBManager.DB_NAME + "." + DBManager.ColumnNames.USERS.toString().toLowerCase() + " (username, pass, email) VALUES ( ? , ? , ? )" );
			st.setString(1, user.getUserName());
			st.setString(2, user.getPassword());
			st.setString(3, user.getEmail());
			st.execute();
		
		} catch (SQLException e) {
			System.out.println("Error executing the statement in addUser: " + e.getMessage());
		} finally {
			try {
				st.close();
			} catch (SQLException e) {}
		}
	}

	@Override
	public List<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT username, user_id, pass, email FROM " + DBManager.DB_NAME + "." + DBManager.ColumnNames.USERS.toString().toLowerCase() + ";");
			
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("pass");
				final int uniqueDBId = rs.getInt("user_id");
				String email = rs.getString("email");
				User newUser = new User(username, password, uniqueDBId, email);
				users.add(newUser);
			}
			
		} catch (SQLException e) {
			System.out.println("Error executing the statement in getAllUsers: " + e.getMessage());
		} 
		
		return users;
	}


	@Override
	public boolean checkIfUserExists(String username) {
		for(User u : this.allUsers) {
			if (u.getUserName().equals(username)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public boolean checkIfEmailExists(String email) {
		for(User u : this.allUsers) {
			if (u.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public void registerUser(User user) {
		
		this.allUsers.add(user); // adds user to cache
		addUser(user); // adds user to DB
		
	}

}
