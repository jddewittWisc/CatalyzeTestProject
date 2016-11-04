package com.dewitt.service;

import java.nio.file.AccessDeniedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.dewitt.configuration.AuthenticationHelper;
import com.dewitt.configuration.Representation;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private String user;
	private String fullName;
	private String emailAddress;
	Connection connection = null;
	AuthenticationHelper authHelper = AuthenticationHelper.getAuthenticationHelper();

	@GET
	public Representation returnUser() throws AccessDeniedException {
		String content = "";

		if (user == null)
			throw new AccessDeniedException("Access denied");

		content = String.format(user, fullName, emailAddress);

		return new Representation(content);
	}

	@POST
	public Representation createUser(@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("fullname") String fullName,
			@QueryParam("emailAddress") String emailAddress) {

		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			String passwordEncrypted = authHelper.encrypt(password);
			
			String insertStatement = "insert into user values('"+username
					+"','"+fullName+"','"+passwordEncrypted+"','"+emailAddress+"')";
			
			statement.executeUpdate(insertStatement);
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

		return new Representation("User created: " + username);
	}

	@PUT
	public Representation updateUser(@QueryParam("fullName") String fullName,
			@QueryParam("emailAddress") String emailAddress) {
		String username = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("select username from login");
			
			while(rs.next()){
				username = rs.getString("username");
			}
			
			
			String updateStatement = "update user set fullname='"+fullName+"',emailaddress='"+emailAddress
					+"' where username='"+username+"'";
			
			statement.executeUpdate(updateStatement);
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
		
		return new Representation("User updated: " + username);
		
	}

	@DELETE
	public Representation deleteUser() {
		
		String username = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("select username from login");
			
			while(rs.next()){
				username = rs.getString("username");
			}
			
			
			String deleteStatement = "delete from user where username='"+username+"'";
			
			statement.executeUpdate(deleteStatement);
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
		return new Representation("User deleted: "+ username);
	}

	public UserResource(String user, String fullName, String emailAddress) {
		this.user = user;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
	}
}
