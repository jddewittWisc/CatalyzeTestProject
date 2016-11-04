package com.dewitt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.dewitt.configuration.CatalyzeTestConfig;
import com.dewitt.service.AuthenticationResource;
import com.dewitt.service.BaseResource;
import com.dewitt.service.UserResource;

public class CatalyzeTestApplication extends Application<CatalyzeTestConfig> {

	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists user");
			statement.executeUpdate("drop table if exists login");
			statement.executeUpdate("create table user (username string, fullname string, password string, emailaddress string )");
			statement.executeUpdate("create table login (username string)");
			//initialize database to create tables
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
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
	}

	@Override
	public void run(CatalyzeTestConfig config, Environment environment) {
		
		final BaseResource baseResource = new BaseResource(config.getUser(),
				config.getFullName(), config.getEmailAddress());
		environment.jersey().register(baseResource);

		final UserResource userResource = new UserResource(config.getUser(),
				config.getFullName(),config.getEmailAddress());
		environment.jersey().register(userResource);
		
		final AuthenticationResource authResource = new AuthenticationResource();
		environment.jersey().register(authResource);
	}

	@Override
	public void initialize(Bootstrap<CatalyzeTestConfig> bootstrap) {

	}

}
