package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.ContractDao;
import com.model.Contract;
import com.utils.AppException;
import com.utils.DBUtil;

/**
 * Contract data access layer implementation class
 */
public class ContractDaoImpl implements ContractDao {

	/**
	 * Add contract information
	 * 
	 * @param contract 
	 * @return boolean Return true if successful , otherwise false
	 * @throws AppException
	 */
	public boolean add(Contract contract) throws AppException{
		boolean flag = false;// Operation flag
		// Declare database connection object, pre-compiled object and results set object
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			// Create database connection
			conn = DBUtil.getConnection();
			//Declare operation statement,save contract information, "?" is a placeholder
			String sql = "insert into t_contract" 
				+"(user_id,customer,num,name,beginTime,endTime,content) "
				+"values(?,?,?,?,?,?,?)";
				
			// Pre-compiled sql, and return primary key
            psmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 

			// Set values for the placeholder 
			psmt.setInt(1, contract.getUserId());
			psmt.setString(2, contract.getCustomer());
			psmt.setString(3, contract.getNum());
			psmt.setString(4, contract.getName());
			// Turn java.util.Dat to java.sql.Date
			java.sql.Date beginTime = new java.sql.Date(contract.getBeginTime().getTime());
			java.sql.Date endTime = new java.sql.Date(contract.getEndTime().getTime());
			psmt.setDate(5, beginTime);
			psmt.setDate(6, endTime);
			psmt.setString(7, contract.getContent());
			
			psmt.executeUpdate();// Execute update 
			rs = psmt.getGeneratedKeys();  //Get primary key in  insert row,only one record in result set

			if (rs.next()) {
				contract.setId(rs.getInt(1));// Get primary key's value,and set it into contract object
				flag = true; // If affected lines greater than 0, so operation success
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
			"com.ruanko.dao.impl.ContractDaoImpl.add");
		} finally {
			// Close database object operation, release resources
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		return flag;
	}
	
	/**
	 * Query contract object according to contract id
	 * 
	 * @param id Contract id
	 * @return Contract object
	 * @throws AppException
	 */
	public Contract getById(int id) throws AppException {
		// Declare contract
		Contract contract = null;
		
		// Declare database connection object, pre-compiled object and result set object
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			// Create database connection
			conn = DBUtil.getConnection();
			//Define SQL statement: query contract information according to the contract id 
			String sql = "select id,name,user_id,customer,num,beginTime,endTime,content "
					+"from t_contract "
					+"where id = ? and del = 0";

			// Pre-compiled sql, and set the parameter values
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, id); //Set contract id
			
			// Query result set
			rs = psmt.executeQuery();

			//Get information in result set by loop,and encapsulated into contract object
			if(rs.next()) {
				contract = new Contract();
				contract.setId(rs.getInt("id"));
				contract.setName(rs.getString("name"));
				contract.setUserId(rs.getInt("user_id"));
				contract.setCustomer(rs.getString("customer"));
				contract.setNum(rs.getString("num"));
				contract.setBeginTime(rs.getDate("beginTime"));
				contract.setEndTime(rs.getDate("endTime"));
				contract.setContent(rs.getString("content"));	
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
					"com.ruanko.dao.impl.ContractDaoImpl.getById");
		} finally {
			//  Close the database operation object
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		return contract;
	}
	
	/**
	 * Query contract id set according to user id
	 * 
	 * @param id Contract id
	 * @return Contract id set
	 * @throws AppException
	 */
	public List<Integer> getIdsByUserId(int userId) throws AppException {
		// Initialize id set
		List<Integer> ids = new ArrayList<Integer>();
		// Declare database connection object, pre-compiled object and result set object
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			// Create database connection
			conn = DBUtil.getConnection();
			// Declare operation statement,query contract id according to user id, "?" is a Placeholder
			String sql = "select id "
					+"from t_contract "
					+"where user_id = ? and del = 0";
			// Pre-compiled sql
			psmt = conn.prepareStatement(sql);
			// Set values for the placeholder '?'
			psmt.setInt(1, userId);
			// Query result set
			rs = psmt.executeQuery();
			
			// Get information in result set by loop,and save it to conIds
			while (rs.next()) {
				ids.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("com.ruanko.dao.impl.ContractDaoImpl.getIdsByUserId");
		} finally {
			// Close database object operation, release resources
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		return ids;
	}
	
	/**
	 * Update contract's content according to contract id,passing parameters through entity object 
	 * 
	 * @param conId Contract id
	 * @return boolean Return true if successful , otherwise false
	 * @throws AppException
	 */
	public boolean updateById(Contract contract) throws AppException {
		boolean flag = false;// Operation flag
		// Declare database connection object, pre-compiled object
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			// Create database connection
			conn = DBUtil.getConnection();
			// Declare sql:update contract information according to contract id
			String sql = "update t_contract set name = ?, customer = ?, beginTime = ?, endTime = ?, content = ? " 
					+"where id = ? and del = 0";

			// Pre-compiled sql, and set the parameter values
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, contract.getName());
			psmt.setString(2, contract.getCustomer());
			// Turn java.util.Dat to java.sql.Date
			java.sql.Date beginTime = new java.sql.Date(contract.getBeginTime().getTime());
			java.sql.Date endTime = new java.sql.Date(contract.getEndTime().getTime());
			psmt.setDate(3, beginTime);
			psmt.setDate(4, endTime);
			psmt.setString(5, contract.getContent());
			psmt.setInt(6, contract.getId());

			// Execute update,return affected rows
			int count = psmt.executeUpdate();
			
			if (count > 0) {// If affected lines greater than 0, so update success
				flag = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("com.ruanko.dao.impl.ContractDaoImpl.updateById");
		} finally {
			// Close database operation object
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		return flag;
	}
	
	public List<Contract> getAll() throws AppException {
		// Initialiaze roleList
		List<Contract> contractList = new ArrayList<Contract>();
		
		//Declare Connection object,PreparedStatement object and ResultSet object
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			// Create database connection
			conn = DBUtil.getConnection();
			// Declare operation statement:query all role object set,"?" is a placeholder
			String sql = "select id,user_id,customer,num,name,beginTime,endTime,content,del from t_contract ";
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();// Return result set
			// Loop to get information in result set,and save in ids
			while (rs.next()) {
				Contract contract = new Contract(); // Instantiate role object
				// Set value to role
				contract.setId(rs.getInt("id"));
				contract.setUserId(rs.getInt("user_id"));
				contract.setContent(rs.getString("content"));
				contract.setCustomer(rs.getString("customer"));
				contract.setBeginTime(rs.getDate("beginTime"));
				contract.setEndTime(rs.getDate("endTime"));
				contract.setNum(rs.getString("num"));
				contract.setName(rs.getString("name"));
				contract.setDel(rs.getInt("del"));
				
				
				contractList.add(contract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close the database operation object, release resources
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		return contractList;
	}

	public List<Contract> getFinalized() throws AppException {
		// Initialiaze roleList
		List<Contract> contractList = new ArrayList<Contract>();
		
		//Declare Connection object,PreparedStatement object and ResultSet object
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			// Create database connection
			conn = DBUtil.getConnection();
			// Declare operation statement:query all role object set,"?" is a placeholder
			String sql = "select t_contract.id,user_id,customer,num,name,beginTime,endTime,content,t_contract.del from t_contract,t_contract_state where t_contract.id=t_contract_state.con_id and t_contract_state.type=5 ";
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();// Return result set
			// Loop to get information in result set,and save in ids
			while (rs.next()) {
				Contract contract = new Contract(); // Instantiate role object
				// Set value to role
				contract.setId(rs.getInt("id"));
				contract.setUserId(rs.getInt("user_id"));
				contract.setContent(rs.getString("content"));
				contract.setCustomer(rs.getString("customer"));
				contract.setBeginTime(rs.getDate("beginTime"));
				contract.setEndTime(rs.getDate("endTime"));
				contract.setNum(rs.getString("num"));
				contract.setName(rs.getString("name"));
				contract.setDel(rs.getInt("del"));
				
				
				contractList.add(contract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close the database operation object, release resources
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		return contractList;
	}
	public void deleteContract(int id){
		//Declare Connection object,PreparedStatement object and ResultSet object
		Connection conn = null;
		PreparedStatement psmt = null;
		int rs;
		try {
			// Create database connection
			conn = DBUtil.getConnection();
			// Declare operation statement,query role's information based on role id, "?" is a placeholder
			String sql = "delete from t_contract where id =? " ;
			// Pre-compiled sql
			psmt =(PreparedStatement) conn.prepareStatement(sql);
			// Set values for the placeholder  '?'
			psmt.setInt(1, id);
			
			rs = psmt.executeUpdate();
			
			// Save user's information by using Pole entity object when queried the results set 
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			// Close the database operation object, release resources
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		
	}
	
}
