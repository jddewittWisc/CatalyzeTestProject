package com.dewitt.service;

import java.nio.file.AccessDeniedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import com.dewitt.CatalyzeTestUser;
import com.dewitt.configuration.AuthenticationHelper;
import com.dewitt.configuration.Representation;


@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

	Connection connection = null;
	
	@POST
	public Representation login(@QueryParam("username") String username, @QueryParam("password") String password) throws AccessDeniedException{
	
		AuthenticationHelper authHelper = AuthenticationHelper.getAuthenticationHelper();
		String encryptedPassword = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("select password from user where username='"+username+"'");
			
			while(rs.next()){
				encryptedPassword = rs.getString("password");
			}
			
			if(authHelper.encrypt(password).equals(encryptedPassword)){
				String insertStatement = "insert into login values ('"+username+"')";
				statement.executeUpdate(insertStatement);
			}else{
				throw new AccessDeniedException("Access denied");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		
		return new Representation("Logged in: "+ username);
	}
	
	@DELETE
	public Representation logout(){
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			statement.executeUpdate("delete from login");
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		return new Representation("All users logged out");
	}
}
