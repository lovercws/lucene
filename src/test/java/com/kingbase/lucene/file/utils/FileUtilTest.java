package com.kingbase.lucene.file.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class FileUtilTest extends TestCase{

	public void testListFiles() throws FileNotFoundException{
		FileUtil fileUtil=new FileUtil();
		List<File> listFiles = fileUtil.listFiles("E:\\", false);
		for (File file : listFiles) {
			System.out.println(file.getPath());
		}
		System.out.println(listFiles.size());
	}
	
	public void testListIncludesFiles() throws FileNotFoundException{
		FileUtil fileUtil=new FileUtil();
		
		ArrayList<String> list = new ArrayList<String>();
		//list.add("txt");
		list.add("png");
		
		List<File> listFiles = fileUtil.listIncludesFiles("E:\\", true,list);
		for (File file : listFiles) {
			System.out.println(file.getPath());
		}
		System.out.println(listFiles.size());
	}
}
