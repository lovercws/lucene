package com.kingbase.lucene.commons.analyzer.chinese.ik;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/** IKAnalyzer 中文分词器解析 */
public class IKAnalyzerParseTest {

	private static final Logger log=Logger.getLogger("IKAnalyzerParseTest");
	/** 基于lucene的分析  */
	public static void LuceneParse(String str) throws IOException{
		log.info("start building index.....");
		long start=System.currentTimeMillis();
		IKAnalyzer5x analyzer=new IKAnalyzer5x(true);
		StringReader reader = new StringReader(str);
		TokenStream tokenStream = analyzer.tokenStream("",reader );
		tokenStream.reset();
		//添加属性
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		while(tokenStream.incrementToken()){
			System.out.print(charTermAttribute.toString()+"|");
		}
		System.out.println();
		analyzer.close();
		reader.close();
		log.info("end building index.....");
		long end=System.currentTimeMillis();
		log.info("total spend time is "+(end-start));
	}
	
	/** 独立使用IKAnalyzer分析 */
	public static void IKParse(String str) throws IOException{
		log.info("start building index.....");
		long start=System.currentTimeMillis();
		StringReader stringReader = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(stringReader,true);
		Lexeme lex = null;
		while((lex=ik.next())!=null){
		  System.out.print(lex.getLexemeText()+"|");
		}
		stringReader.close();
		log.info("rend building index.....");
		long end=System.currentTimeMillis();
		log.info("total spend time is "+(end-start));
	}
	
	public static void main(String[] args) throws IOException {
		IKAnalyzerParseTest.IKParse("冯权国是北京大学生节的替补的主持人");
	}
}
