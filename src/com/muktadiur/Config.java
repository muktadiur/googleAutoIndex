package com.muktadiur;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private String email;
	private String password;
	private String path;
	private String file;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static Config getConfig(){
		Config config = new Config();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = Config.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			config.setEmail(prop.getProperty("email"));
			config.setPassword(prop.getProperty("password"));
			config.setPath(prop.getProperty("path"));
			config.setFile(prop.getProperty("file"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return config;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}

	
}
