package com.kingbase.lucene.commons.analyzer.chinese.mmseg4j;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingbase.lucene.commons.searcher.SimpleSearcher;
import com.kingbase.lucene.file.utils.IndexUtil;

import junit.framework.TestCase;

public class Mmseg4jTest extends TestCase{

	String configName="lucene_mmseg_simple";
	
	public void testCreate(){
		IndexUtil indexUtil=new IndexUtil();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", "1");
		map.put("fileName", "中国");
		map.put("content", new StringReader("中华人民共和国万岁"));
		list.add(map);
		indexUtil.createIndex(configName, list);
	}
	
	public void testSearch() throws IOException{
		SimpleSearcher searcher=new SimpleSearcher();
		List<Map<String,Object>> termQuery = searcher.termQuery(configName, "fileName", "中国", Integer.MAX_VALUE);
		System.out.println(termQuery);
	}
}
