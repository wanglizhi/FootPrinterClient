package tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ui.foundation.BasePanel;

public class MapUtils {

	public MapUtils() {
		
	}
	
	public static ImageIcon getMap(String center, int zoom, ArrayList<String> markers, String type, int width, int height, int scale){
		String mapurl = "http://maps.googleapis.com/maps/api/staticmap?&language=zh-CN&sensor=false" + Config.APP_KEY;
		if (center != null)
			mapurl += "&center=" + FileUtils.translate(center);
		if (zoom != 0)
			mapurl += "&zoom="+ zoom;
		if (width != 0 && height != 0)
			mapurl += "&size=" + width  + "x" + height;
		if (scale != 0)
			mapurl += "&scale=" + scale;
		if (type != null)
			mapurl += "&maptype="+ type;
		for (String marker : markers)
			mapurl += "&markers=" + FileUtils.translate(marker); 

			if (Config.NETWORK == 1)
			
				try {
					System.out.println("WHAT HAPPEND!"+mapurl);
					return new ImageIcon(new URL(mapurl));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return BasePanel.createFixedSizeImage("img/network.gif", width, height);

	}
	
	public static ImageIcon getMap(ArrayList<String> markers, int width, int height){
		return getMap(null, 0, markers, null, width, height, 0);
	}
	
	public static ImageIcon getMap(final String marker, int width, int height){
		return getMap(new ArrayList<String>(){/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{add(marker);}}, width, height);
	}

}
