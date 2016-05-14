package com.kingbase.lucene.commons.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.Mmseg4jAnalyzerFactory;

/**
 * 获取项目配置的分词器
 * 
 * @author ganliang
 *
 */
public class AnalyzerConfig {

	/**
	 * @param analyzerName
	 *            分词器的名称
	 * @return
	 */
	public Analyzer analyzer(String analyzerName) {
		// 默认选择 StandardAnalyzer
		if (analyzerName == null || "".equals(analyzerName)) {
			return new StandardAnalyzer();
		}
		Analyzer analyzer = null;
		switch (analyzerName) {
		// 空格分词器 (以空格分割语汇单元)
		case "WHITESPACEANALYZER":
			analyzer = new WhitespaceAnalyzer();
			break;
		// 简单分词器 (以非字符分割语汇单元 并将语汇单元转化为小写)
		case "SIMPLEANALYZER":
			analyzer = new SimpleAnalyzer();
			break;
		// 停分词分词器 (以非字符分割语汇单元 并将语汇单元转化为小写 并且去除停分词的语汇单元)
		case "STOPANALYZER":
			analyzer = new StopAnalyzer();
			break;
		// 标准分词器
		case "STANDARDANALYZER":
			analyzer = new StandardAnalyzer();
			break;
		// 关键字分词器 将输入的字符流 生成一个语汇单元
		case "KEYWORDANALYZER":
			analyzer = new KeywordAnalyzer();
			break;
		case "MMSEGSIMPLEANALYZER":
			analyzer = Mmseg4jAnalyzerFactory.simpleAnalyzer();
			break;
		case "MMSEGCOMPLEXANALYZER":
			analyzer = Mmseg4jAnalyzerFactory.complexAnalyzer();
			break;
		default:
			analyzer = new StandardAnalyzer();
			break;
		}
		return analyzer;
	}
}
