package com.kingbase.lucene.commons.analyzer.chinese.paoding;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

/**
 * paoding 中文分词器
 * @author ganliang
 */
public class PaodingAnalyzerFactory {

	private static PaodingAnalyzer analyzer=null;
	
	private PaodingAnalyzerFactory(){
	}
	
	/**
	 * 单例 一个PaodingAnalyzer
	 * @return
	 */
	public synchronized static Analyzer instance(){
		if(analyzer==null){
			analyzer=new PaodingAnalyzer("classpath:paoding/paoding-analysis.properties");
		}
		return analyzer;
	}
	
	public static void testPaodingAnalyzer() throws IOException{
	   TokenStream tokenStream = analyzer.tokenStream("", new StringReader("中华人民共和国万岁"))	;
	   tokenStream.reset();
	   
	   CharTermAttribute termAttr = tokenStream.addAttribute(CharTermAttribute.class);
	   
	   while(tokenStream.incrementToken()){
		   String term = termAttr.toString();
		   
		   System.out.println(term);
	   }
	   analyzer.close();
	}
	
	public static void main(String[] args) {
		PaodingAnalyzerFactory.instance();
		try {
			testPaodingAnalyzer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
