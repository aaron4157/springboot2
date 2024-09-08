package idv.ytchang.springboot2.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class FileService {
	
	public enum FileTypes { ATTACHMENT, SOURCE, POST};
	private final String UPLOAD_DIRECTORY = "c:\\springboot2";
	private static final Logger log= LoggerFactory.getLogger(FileService.class);

	public FileService() {
		
	}
	
	/**
	 * persist file from formdata
	 * @param file
	 * @param serverName
	 * @throws NullPointerException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void uploadFile(MultipartFile file, String serverName) throws NullPointerException, IllegalStateException, IOException {
		String fileName = file.getOriginalFilename();
		if(serverName != null && !"".equals(serverName)) {
			String extension = StringUtils.getFilenameExtension(fileName);
			fileName = serverName + "." + extension;
		} 
		File serverFile = new File(UPLOAD_DIRECTORY,fileName);
		file.transferTo(serverFile);
		
	}
	
	/**
	 * push file to front-end
	 * @param response HttpServletResponse
	 * @param file File
	 * @param fileType FyleTypes
	 * @param displayFileName optional 
	 */
	public void fileOutput(HttpServletResponse response, File file, FileTypes fileType, String displayFileName) {
		
		try (BufferedInputStream bins = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream bouts = new BufferedOutputStream(response.getOutputStream())){	
			
			String fileName2 = ((displayFileName == null || "".equals(displayFileName)) ? file.getName() : displayFileName);		
			byte[] bytes = new byte[bins.available()];
			
			bins.read(bytes);
			bins.close();
			
			switch(fileType) {
			case SOURCE: // un-readable source; need further processing by browser			
				response.setContentType("blob");
				response.setContentLengthLong(bytes.length);
				break;
			case POST: // readable on browser
				String memeType = URLConnection.guessContentTypeFromName(fileName2);
				response.setContentType(memeType);
				response.setContentLengthLong(bytes.length);
				break;
			default: // attach file
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName2, "UTF-8"));
				response.setContentLength(bytes.length);		
			}
			
			bouts.write(bytes);
			bouts.flush();
		} catch (NullPointerException | FileNotFoundException e1) {
			log.error("fileOutput cannot read the file: " + file);
		} catch (Exception e) {
			log.error("fileOutput failed: " + e);
		} 
		
	}

}
