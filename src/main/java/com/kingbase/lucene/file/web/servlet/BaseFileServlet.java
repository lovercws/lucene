package com.kingbase.lucene.file.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.kingbase.lucene.file.service.IBaseFileService;
import com.kingbase.lucene.file.service.impl.BaseFileServiceImpl;
import com.kingbase.lucene.file.utils.FileUtil;

/**
 * 基本检索
 * @author lgan
 */
@WebServlet(urlPatterns = { "/file/BaseFileServlet.action" })
public class BaseFileServlet extends HttpServlet {

	private static final Properties properties=new Properties();
	
	private static final long serialVersionUID = 7930573704078335991L;
	private static final Logger log=Logger.getLogger(BaseFileServlet.class);
	private static final String LUCENE_FILE_BASE = "lucene_file_base";// 索引配置文件

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		InputStream stream =BaseFileServlet.class.getResourceAsStream("/fileupload.properties");
		if(stream==null){
			log.warn("fileupload.properties配置文件不存在");
		}else{
			try {
				properties.load(stream);
			} catch (IOException e) {
			}
		}
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		if (type == null) {
			throw new IllegalArgumentException();
		}

		switch (type) {
		// 进入到索引目录
		case "index":
			response.sendRedirect(request.getContextPath() + "/file/base/createIndex.jsp");
			break;
		// 添加索引
		case "addIndex":
			addIndex(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * 添加索引
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void addIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String tempPath=properties.getProperty("tempPath");//临时目录的名称
		if(tempPath==null){
			tempPath="/temp";
		}
		String filePath=request.getSession().getServletContext().getRealPath(tempPath);//临时目录的绝对路径
		File tempFile = new File(filePath);
		if(!tempFile.exists()){
			tempFile.mkdirs();//如果临时文件不存在 则级联创建
	    }
		
		String maxSize=properties.getProperty("maxSize");//上传文件的最大字节
		int maxSizeMB=2;//默认最大上传文件大小
		if(maxSize==null){
			maxSizeMB=Integer.parseInt(maxSize);
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 最大缓存
		factory.setSizeThreshold(maxSizeMB * 1024);
		// 设置临时文件目录
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 文件最大上限
		upload.setSizeMax(maxSizeMB * 1024 * 1024);
		try {
			List<File> files=new ArrayList<File>();
			// 获取所有文件列表
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					// 文件名
					String fileName = item.getName();
					File file = new File(filePath,fileName);
					item.write(file);
					files.add(file);
				}
			}
			//添加索引
			IBaseFileService baseFileService=new BaseFileServiceImpl();
			boolean success=baseFileService.addIndexes(LUCENE_FILE_BASE,files);
			
			//删除临时文件
			FileUtil fileUtil=new FileUtil();
			fileUtil.deleteFiles(files);
			
			out.print("success:"+success);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("success:false");
		}
	}

}
