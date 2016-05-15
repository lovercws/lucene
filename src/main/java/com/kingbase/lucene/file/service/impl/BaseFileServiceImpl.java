package com.kingbase.lucene.file.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.kingbase.lucene.file.service.IBaseFileService;
import com.kingbase.lucene.file.utils.FetchFileDataUtil;
import com.kingbase.lucene.file.utils.IndexUtil;

public class BaseFileServiceImpl implements IBaseFileService{

	@Override
	public boolean addIndexes(String configName, List<File> files) {
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

}
