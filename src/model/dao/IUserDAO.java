package model.dao;

import java.util.List;
import model.user.User;

public interface IUserDAO {
	
	void addUser(User user);
	List<User> getAllUsers();
	void registerUser(String username, String password, String email);
	boolean checkIfUserExists(String username);
}
