package pos;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

public class SQL_Connection {

	private Connection myConn;
	
	public SQL_Connection() throws Exception 
	{
		
		
		String user = "rooot";
		String password = "123";
		String dburl = "jdbc:mysql://localhost:3306/login";
		
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
	}
	
	
	public void addReceipt(String name, BigDecimal total, String date) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into receipt"
					+ " (Name, Total, Date)"
					+ " values (?, ?, ?)");
			
			// set params
			myStmt.setString(1, name);
		    myStmt.setBigDecimal(2, total);
			myStmt.setString(3, date);
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public void deleteAllReceipt() throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from receipt");
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void deleteAllCart() throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from carttable");
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void deleteCart(int Id) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from carttable where ID=?");
			
			// set param
			myStmt.setInt(1, Id);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
public int getAmountProductValue(int id) {
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
        int amount = 0;
		try {
			
			myStmt = myConn.prepareStatement("select amountProduct from producttable where ID like ?");
			myStmt.setInt(1, id);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
			 
				amount = myRs.getInt("amountProduct");
				
			}
			
		}catch (Exception e) {
            System.err.println(e);
            }
		return amount;
		
		}

	public int getAmountCartValue(int id) {
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
        int amount = 0;
		try {
			
			myStmt = myConn.prepareStatement("select amountProduct from carttable where ID like ?");
			myStmt.setInt(1, id);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
			 
				amount = myRs.getInt("amountProduct");
				
			}
			
		}catch (Exception e) {
            System.err.println(e);
            }
		return amount;
		
		}
	
	
	public void updateAmountCart(int amount, int previousamount) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update carttable"
					+ " set amountProduct=?"
					+ " where ID=?");
			
			// set parameters
			myStmt.setInt(1, amount);
			myStmt.setInt(2, previousamount);
			
			// execute SQL
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public List<Data_Constructor> getAllCart() throws Exception 
	{
		List<Data_Constructor> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from carttable");
			
			while (myRs.next()) {
				Data_Constructor tempEmployee = find_info(myRs);
				list.add(tempEmployee);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void addCart(int id, String productName, int amount,String category, BigDecimal cost) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into carttable"
					+ " (ID, productName, amountProduct, CategoryProducts, costProduct)"
					+ " values (?, ?, ?, ?, ?)");
			
			// set params
			myStmt.setInt(1, id);
		    myStmt.setString(2, productName);
			myStmt.setInt(3, amount);
			myStmt.setString(4, category);
			myStmt.setBigDecimal(5, cost);
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public void updateAmountProduct(int amount, int id) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update producttable"
					+ " set amountProduct=?"
					+ " where ID=?");
			
			// set params
			myStmt.setInt(1, amount);
			myStmt.setInt(2, id);
			
			// execute SQL
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public void deleteProducts(int Id) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from producttable where ID=?");
			
			// set param
			myStmt.setInt(1, Id);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void updateProducts(Data_Constructor theEmployee) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update producttable"
					+ " set ID=?, productName=?, amountProduct=?, CategoryProducts=?, costProduct=?"
					+ " where ID=?");
			
			// set params
			myStmt.setInt(1, theEmployee.getId());
			myStmt.setString(2, theEmployee.getProductName());
			myStmt.setInt(3, theEmployee.getamountProduct());
			myStmt.setString(4, theEmployee.getcategoryProducts());
			myStmt.setBigDecimal(5, theEmployee.getcost());
			myStmt.setInt(6, theEmployee.getId());
			// execute SQL
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
		
	}
	
	
	public void addProducts(Data_Constructor theEmployee) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into producttable"
					+ " (ID, productName, amountProduct, CategoryProducts, costProduct)"
					+ " values (?, ?, ?, ?, ?)");
			
			// set params
			myStmt.setInt(1, theEmployee.getId());
			myStmt.setString(2, theEmployee.getProductName());
			myStmt.setInt(3, theEmployee.getamountProduct());
			myStmt.setString(4, theEmployee.getcategoryProducts());
			myStmt.setBigDecimal(5, theEmployee.getcost());
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public List<Data_Constructor> getAllProducts() throws Exception {
		List<Data_Constructor> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from producttable");
			
			while (myRs.next()) {
				Data_Constructor tempEmployee = find_info(myRs);
				list.add(tempEmployee);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	

	public List<Data_Constructor> searchCategoryProducts(String category) throws Exception {
		List<Data_Constructor> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			category += "%";
			myStmt = myConn.prepareStatement("select * from producttable where CategoryProducts like ?");
			myStmt.setString(1, category);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Data_Constructor tempEmployee = find_info(myRs);
				list.add(tempEmployee);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	public  List<Data_Constructor> searchIdProducts(String id) throws Exception {
		List<Data_Constructor> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			
			id += "%";
			myStmt = myConn.prepareStatement("select * from producttable where ID like ?");
			myStmt.setString(1, id);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Data_Constructor tempEmployee = find_info(myRs);
				list.add(tempEmployee);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	private Data_Constructor find_info(ResultSet myRs) throws SQLException {
	
		int id = myRs.getInt("ID");
		String productName = myRs.getString("productName");
		BigDecimal costProduct = myRs.getBigDecimal("costProduct");
		int amountProduct = myRs.getInt("amountProduct");
		String CategoryProducts = myRs.getString("CategoryProducts");
		
		Data_Constructor tempEmployee = new Data_Constructor(id, productName, amountProduct, CategoryProducts,costProduct);
		
		return tempEmployee;
	}

	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}
	private void close(Statement myStmt) throws SQLException {
		close(null, myStmt, null);		
	}

}
