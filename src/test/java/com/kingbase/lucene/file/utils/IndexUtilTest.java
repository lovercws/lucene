package com.kingbase.lucene.file.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

public class IndexUtilTest extends TestCase{

	String configName="lucene_directory_fs";
	String directory="E:/workspace/lgan";
	
	public void testCreateIndex() throws FileNotFoundException{
		IndexUtil indexUtil=new IndexUtil();
		
		indexUtil.createIndex(configName, directory);
	}
	
	public void testClear() throws IOException{
		IndexUtil indexUtil=new IndexUtil();
		
		indexUtil.clear(configName);
	}
	
}
