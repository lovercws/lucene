package com.kingbase.lucene.file.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 * @author ganliang
 */
public class XmlUtil {

	public Document getDocumnet(InputStream stream) throws DocumentException{
		SAXReader saxReader=new SAXReader();
		return saxReader.read(stream);
	}
}
