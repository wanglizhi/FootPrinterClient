package tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {

	public FileUtils() {
		// TODO Auto-generated constructor stub
	}

	public static byte[] file2String(File file) {
		byte[] b = null;
		try {
			b = new byte[(int) file.length()];
			BufferedInputStream is = new BufferedInputStream(
					new FileInputStream(file));
			Debugger.log("bytes : " + b.length);
			Debugger.log("string :" + new String(b).getBytes("GBK").length);
			is.read(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	//	public static String file2String(File file) {
//		InputStreamReader reader = null;
//		StringWriter writer = new StringWriter();
//		try {
//			reader = new InputStreamReader(new FileInputStream(file));
//			char [] buffer = new char[2048];
//			int n = 0;
//			while (-1 != (n = reader.read(buffer))){
//				writer.write(buffer, 0, n);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if (reader != null)
//				try{
//					reader.close();
//				} catch (IOException e2) {
//					e2.printStackTrace();
//				}
//		}
//		
//		return writer.toString();
//	}

	public static String translate(String str) {
			byte[] bytes  = null;
			try {
				bytes = str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String s = "";
			
			for(byte b : bytes){
				s += ("%" + Integer.toHexString(b & 0xFF));
			}
//			Debugger.log(s);
		return s;
	}
	
	public static  boolean isConnect(URL url) {   
        HttpURLConnection con=null;
        int state=-1;
        try {  
            con = (HttpURLConnection) url.openConnection();  
            state = con.getResponseCode();  
            if (state == 200) {  
                return true;
            }
        }catch (Exception ex) {  
    }  
    return false;  
}  

}
