package com.kingbase.lucene.commons.configuration;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.store.Directory;

import com.kingbase.lucene.commons.analyzer.AnalyzerConfig;
import com.kingbase.lucene.commons.directory.DirectoryConfig;

/**
 * 读取配置信息
 * @author ganliang
 */
public class ReadConfig {

	private static Map<String,Map<String,Object>> mapData=ConfigInitialization.mapData;
	
    /**
     * @param configName 配置名称
     * @return 获取字段的集合
     */
	@SuppressWarnings({ "unchecked"})
	public Map<String,Map<String,String>> getFields(){
		checkMapData();
		Map<String, Object> configMap =mapData.get(configName.toUpperCase());
		return (Map<String, Map<String, String>>) configMap.get(Config.FIELDS);
    }
	
	/**
	 * @param configName 配置名称
	 * @param fieldName 字段名称
	 * @return 字段的属性集合
	 */
	public Map<String,String> getField(String fieldName){
		Map<String, String> map=null;
		Map<String, Map<String, String>> fieldsMap = getFields();
		for(String field:fieldsMap.keySet()){
			if(field.equalsIgnoreCase(fieldName)){
				map= fieldsMap.get(field);
				break;
			}
		}
		return map;
	}
	
	/**
	 * 获取项目中使用的分词器
	 */
	public Analyzer getAnalyzer(){
		checkMapData();
		Map<String, Object> configMap =mapData.get(configName.toUpperCase());
		String analyzer = configMap.get(Config.ANALYZER).toString().toUpperCase();
		AnalyzerConfig analyzerUtil=new AnalyzerConfig();
		//获取配置的分词器
		return analyzerUtil.analyzer(analyzer);
	}
	
	/**
	 * 获取Directory
	 * @return
	 */
	public Directory getDirectory(){
		String dir = getDir();
		Map<String, Object> configMap =mapData.get(configName.toUpperCase());
		String directoryType = configMap.get(Config.DIRECTORY_TYPE).toString();
		
		DirectoryConfig config=new DirectoryConfig();
		return config.directory(directoryType,dir,configName);
	}
	
	/**
	 * @param configName 配置名称
	 * @return 索引保存的位置[绝对路径]
	 */
	public String getDir(){
		checkMapData();
		Map<String, Object> configMap =mapData.get(configName.toUpperCase());
		String directory = configMap.get(Config.DIRECTORY).toString().toLowerCase();
		//如果配置的是绝对路径 则直接返回
		File file = new File(directory);
		boolean isDirectory = file.isDirectory();
		if(!isDirectory){
			String path = ConfigInitialization.class.getResource("/").getPath();
			directory=path+directory;
		}
		return  directory;
	}
	
    /**
     * 获取lucene的版本号
     */
	public String getVersion(){
		checkMapData();
		System.out.println(mapData);
		Map<String, Object> configMap =mapData.get(configName.toUpperCase());
		return configMap.get(Config.VERSION).toString();
	}
	
	/**
	 * @param fieldName 字段的名称
	 * @return 该字段的类型
	 */
	public Type getType(String fieldName){
		Map<String, String> field = getField(fieldName);
		String fieldType = field.get("type");
		Type type = null;
		if(fieldType!=null){
			switch (fieldType) {
			case "int":
				type=Type.INT;
				break;
			case "float":
				type=Type.FLOAT;
				break;
			case "double":
				type=Type.DOUBLE;
				break;
			case "long":
				type=Type.LONG;
				break;
			case "string":
				type=Type.STRING;
				break;
			case "text":
				type=Type.BYTES;
				break;
			default:
				type=Type.DOC;
			}
		}
		return type;
	}
	
	 /**
     * 检测mapData 是否为空 如果为空 就重新初始化
     */
    private void checkMapData(){
    	//初始化 map
    	if(mapData==null||mapData.size()==0){
    		ConfigInitialization.load();
    		mapData=ConfigInitialization.mapData;
		}		
    }
    
    private String configName;//配置的名称
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public ReadConfig() {
		setDefaultConfigName();
	}
	/**
	 * 默认选中第一个配置文件
	 */
	private void setDefaultConfigName() {
		checkMapData();
		Set<String> keySet = mapData.keySet();
		Object[] array = keySet.toArray();
		if(array!=null&&array.length>0){
			this.configName=String.valueOf(array[0]);
		}
	}

	public ReadConfig(String configName) {
		this.configName = configName;
	}
}
