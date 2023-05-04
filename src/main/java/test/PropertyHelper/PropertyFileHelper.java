package test.PropertyHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileHelper {
	private Properties propObj = null;	
	public PropertyFileHelper(String propName)
	{
		FileInputStream fis = null;
		File f = new File(System.getProperty("user.dir")+"\\src\\main\\resource\\configfile\\"+propName);
		
		try {
			 fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	 propObj = new Properties();
	 
	 try {
		propObj.load(fis);
	} catch (IOException e) {
		e.printStackTrace();
	}	
	}
	
	
	/**
	 * Method takes key from user and returns value from property file
	 * @param key
	 * @return
	 */
	public String getPropertyValueFromFile(String key)
	{
		String val = propObj.getProperty(key);
		return val;
	}
	
	

}
