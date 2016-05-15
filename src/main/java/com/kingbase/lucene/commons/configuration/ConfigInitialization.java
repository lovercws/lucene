package com.kingbase.lucene.commons.configuration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author lgan
 *配置文件的初始化
 */
public class ConfigInitialization{
	
    private static final Logger log=Logger.getLogger(ConfigInitialization.class);
    //读取配置文件信息到内存中
    protected static Map<String,Map<String,Object>> mapData=new HashMap<String,Map<String,Object>>();

    /**
     * 获取配置文件的目录
     * @return
     */
    private static InputStream getConfigStream(){
    	log.debug("加载lucen配置文件【"+Config.CONFIGLOCATION+"】");
    	return ConfigInitialization.class.getResourceAsStream(Config.CONFIGLOCATION);
    }
    
    /**
     * 加载配置文件
     */
    public static void load(){
    	InputStream stream = getConfigStream();
    	ParseConfig parseConfig=new ParseConfig();
    	mapData=parseConfig.parse(stream);
    }
	
}
