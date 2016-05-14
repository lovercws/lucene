package com.kingbase.lucene.commons.analyzer.chinese.mmseg4j;

import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis.ComplexAnalyzer;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis.SimpleAnalyzer;

public class Mmseg4jAnalyzerFactory {
	private static final String path=getMmsegPath();//mmseg4j 配置路径

	public static SimpleAnalyzer simpleAnalyzer(){
		return new SimpleAnalyzer(path);
	}
	
	public static ComplexAnalyzer complexAnalyzer(){
		return new ComplexAnalyzer(path);
	}
	
	/**
	 * 获取配置路径
	 * @return
	 */
	private static String getMmsegPath(){
		return Mmseg4jAnalyzerFactory.class.getResource("/mmseg").getFile();
	}
}
