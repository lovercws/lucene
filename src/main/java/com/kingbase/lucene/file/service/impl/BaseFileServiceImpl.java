package com.kingbase.lucene.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.SortField.Type;

import com.kingbase.lucene.commons.configuration.ReadConfig;
import com.kingbase.lucene.commons.searcher.BaseSearcher;
import com.kingbase.lucene.commons.searcher.SimpleSearcher;
import com.kingbase.lucene.file.service.IBaseFileService;
import com.kingbase.lucene.file.utils.FetchFileDataUtil;
import com.kingbase.lucene.file.utils.IndexUtil;

public class BaseFileServiceImpl implements IBaseFileService{

	private static final Logger log=Logger.getLogger(BaseFileServiceImpl.class);
	@Override
	public boolean addIndexFromUpload(String configName, List<File> files) {
		boolean success=false;
		//从文件中抽取 数据集合
		FetchFileDataUtil fetchFileDataUtil=new FetchFileDataUtil();
		try {
			List<Map<String,Object>> datas = fetchFileDataUtil.fetchFileDatas(configName, files);
			
			//建立索引
			IndexUtil indexUtil=new IndexUtil();
			indexUtil.createIndex(configName, datas);
			success=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean addIndexFromDirectory(String configName, String directory) {
		boolean success=false;
		//从文件中抽取 数据集合
		FetchFileDataUtil fetchFileDataUtil=new FetchFileDataUtil();
		try {
			List<Map<String,Object>> datas = fetchFileDataUtil.fetchFileDatas(configName, directory);
			
			//建立索引
			IndexUtil indexUtil=new IndexUtil();
			indexUtil.createIndex(configName, datas);
			success=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public String search(String configName, String fieldName, String fieldValue) {
		BaseSearcher searcher=new SimpleSearcher();
		ReadConfig config=new ReadConfig(configName);
		List<Map<String, Object>> data=null;
		//获取查询的字段
		Type type = config.getType(fieldName);
		try {
			if(fieldName==null||fieldValue==null||"".equals(fieldName)||"".equals(fieldValue)){
				data = searcher.matchAllDocsQuery(configName);
			}
			//如果是数字  则数字查询
			else if(type==Type.INT||type==Type.FLOAT||type==Type.DOUBLE||type==Type.LONG){
				data=searchNumber(configName,searcher,type,fieldName,fieldValue);
			}else{
				data=searcher.queryParse(configName, fieldName, fieldValue, Integer.MAX_VALUE);
			}
		} catch (Exception e) {
			log.error("查询异常", e);
		}
		String fields=getFields(configName);
		String json="{'metadata':{'fields':[{},{},{}],'columns':[]}}";
		return null;
	}

	private String getFields(String configName) {
		ReadConfig config=new ReadConfig(configName);
		Map<String, Map<String, String>> fields = config.getFields();
		Iterator<String> iterator = fields.keySet().iterator();
		StringBuilder builder=new StringBuilder("[");
		
		while(iterator.hasNext()){
			String fieldName = iterator.next();
			builder.append("{'name':'"+fieldName+"'}");
			if(iterator.hasNext()){
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 数字查询
	 * @param configName
	 * @param searcher
	 * @param type
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private List<Map<String, Object>> searchNumber(String configName, BaseSearcher searcher, Type type, String fieldName, String fieldValue) throws IOException, ParseException {
		Object number=null;
		if(type==Type.INT){
			number=Integer.parseInt(fieldValue);
		}else if(type==Type.FLOAT){
			number=Float.parseFloat(fieldValue);
		}else if(type==Type.DOUBLE){
			number=Double.parseDouble(fieldValue);
		}else if(type==Type.LONG){
			number=Long.parseLong(fieldValue);
		}
		return searcher.numericRangeQuery(configName, fieldName, number, Integer.MAX_VALUE);
	}

}
