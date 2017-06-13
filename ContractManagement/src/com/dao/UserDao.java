package com.dao;

import java.util.List;

import com.model.User;
import com.utils.AppException;

/**
 * User Data Access Layer Interface
 */
public interface UserDao {
	
	/**
	 * Verify whether exist user that  has the same name 
	 * 
	 * @param name  User name
<<<<<<< HEAD
	 * @return Return true if exist user that  has the same name ，otherwise false
	 * @throws AppException
	 */
	public boolean isExist(String name) throws AppException;
	public boolean isExist(int id) throws AppException;
=======
	 * @return Return true if exist user that  has the same name 锛宱therwise false
	 * @throws AppException
	 */
	public boolean isExist(String name) throws AppException;
>>>>>>> origin/LiWenjie
	
	/**
	 * Save user's information
	 * 
	 * @param user User object
	 * @return Return true if save successfully, otherwise false
	 * @throws AppException
	 */
	public boolean add(User user) throws AppException;
	
<<<<<<< HEAD
	public boolean delete(int id) throws AppException;
	
=======
>>>>>>> origin/LiWenjie
	/**
	 * Query  UserId according to user name and password
	 * @param name User name
	 * @param password 
	 * @throws AppException
<<<<<<< HEAD
	 */
	public int login(String name,String password) throws AppException;
	
	/**
	 * Query user's information according to id
	 * 
	 * @param id  User id
	 * @return User 
	 * @throws AppException
	 */
	public User getById(int id) throws AppException;
	
	/**
	 * Query user id set
	 * 
	 * @return User id set
	 * @throws AppException
	 */
	public List<Integer> getIds() throws AppException;
	
	public boolean modify(int id, String password) throws AppException;
	public boolean isExistUser(String name) throws AppException;
	public boolean isSame(String name, String oPassword) throws AppException;
	public boolean ChangePassword(String name, String nPassword) throws AppException;
=======
	 */
	public int login(String name,String password) throws AppException;
	
	/**
	 * Query user's information according to id
	 * 
	 * @param id  User id
	 * @return User 
	 * @throws AppException
	 */
	public User getById(int id) throws AppException;
	
	/**
	 * Query user id set
	 * 
	 * @return User id set
	 * @throws AppException
	 */
	public List<Integer> getIds() throws AppException;
>>>>>>> origin/LiWenjie
	
}
