package model.dao;

import java.util.List;
import model.user.User;

public interface IUserDAO {
	
	void addUser(User user);
	List<User> getAllUsers();
	void registerUser(User user);
	boolean checkIfUserExists(String username);
	boolean checkIfEmailExists(String email);
}
