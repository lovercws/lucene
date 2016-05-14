package com.kingbase.lucene.file.utils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class FetchFileDataUtilTest extends TestCase{

	String configName="lucene_directory_fs";
	
	public void testFetchFileDatas() throws FileNotFoundException{
		FetchFileDataUtil dataUtil=new FetchFileDataUtil();
		List<Map<String,Object>> fetchFileDatas = dataUtil.fetchFileDatas(configName, "E:/workspace");
		for (Map<String, Object> map : fetchFileDatas) {
			System.out.println(map);
		}
	}
	
}
