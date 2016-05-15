package com.kingbase.lucene.file.service;

import java.io.File;
import java.util.List;

public interface IBaseFileService {

	/**
	 * 添加索引
	 * @param configName 配置文件名称
	 * @param files 文件集合
	 * @return 
	 */
	public boolean addIndexes(String configName, List<File> files);

}
