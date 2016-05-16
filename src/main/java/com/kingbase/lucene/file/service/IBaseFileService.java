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
	public boolean addIndexFromUpload(String configName, List<File> files);

	public boolean addIndexFromDirectory(String luceneFileBase, String directory);

	public String search(String luceneFileBase, String fieldName, String fieldValue);

}
