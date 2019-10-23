package com.h2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
//import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/*
 * @Author: Linus Kwong
 * @2019, All Rights Reserved
 * 
 * Description: Loading input data from class path within this program 
 *              or from external files in the file system
 */
@Component
public class LoadInputData implements ResourceLoaderAware{
	@Value("${csvfile.fname1}")
	private String fname1;
	@Value("${csvfile.fname2}")
	private String fname2;
	@Autowired
	private Dao dao;
	
	@Autowired
	private ResourceLoader resourceLoader;

	public void run() throws NumberFormatException, IOException, ParseException, FileNotFoundException {
		String pattern = "MM/dd/yyyy";
		String pattern2 = "yyyyMMdd";
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);

		BufferedReader finBR = null;
		String l; int i=0;
		
		Resource resource = resourceLoader.getResource("file:"+fname1);
		if (!resource.exists()) {
			resource = resourceLoader.getResource("classpath:"+fname1);			
		}
        InputStream inputStream = resource.getInputStream();
		
		try { // loading CellPhone Ownership data
			finBR = new BufferedReader(new InputStreamReader(inputStream));
			
			while ((l = finBR.readLine()) != null) {
				String[] strArray = l.split(",");
				if ((strArray.length >0)&& (i>0)) {
					Cellphone cPhone = Cellphone.create(Integer.valueOf(strArray[0]), strArray[1], sdf2.parse(strArray[2]), strArray[3]);
					dao.addPhone(cPhone);
				} else { System.out.println(i+": no or empty data from CellPhoneOwnership ["+fname1+"]");}
				i++;
			} 
		} finally {
				if (finBR != null) {
					finBR.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
		}

		//Resource resource2 = new ClassPathResource(fname2);
		Resource resource2 = resourceLoader.getResource("file:"+fname2);
		if (!resource2.exists()) {
			resource2 = resourceLoader.getResource("classpath:"+fname2);	
		}
        InputStream inputStream2 = resource2.getInputStream();
		try	{ // loading CellPhone Usage data
			finBR = new BufferedReader(new InputStreamReader(inputStream2));
			i=0;
			while ((l = finBR.readLine()) != null) {			
				String[] strArray = l.split(",");
				if ((strArray.length >0)&& (i>0)) {
					Phoneusage phUsage = Phoneusage.create(Integer.valueOf(strArray[0]), sdf.parse(strArray[1]), Integer.valueOf(strArray[2]),Double.valueOf(strArray[3]));
					dao.addPhoneUsage(phUsage);
				} else { System.out.println(i+": no or empty data from CellPhoneUsage ["+fname2+"]");}
				i++;
			}
		}finally{
			if (finBR != null) {
				finBR.close();
			}
			if (inputStream2 != null) {
				inputStream2.close();
			}
		}
		
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		
	}

}
