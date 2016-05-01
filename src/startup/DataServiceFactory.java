package startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import dataService.*;

/**
 * 这个类暂时作废！！！！！
 * 
 * @author SongShuo
 * 
 */
public class DataServiceFactory {
	private static DataServiceFactory dataServiceFactory;

	public static BlogDataService blogDataService;
	public static CityDataService cityDataService;
	public static PlaceDataService entertainmentDataService;
	public static PlaceDataService hotelDataService;
	public static PlaceDataService landmarkDataService;
	public static PlaceDataService marketDataService;
	public static PlanDataService planDataService;
	public static RecommandDataService recommandDataService;
	public static PlaceDataService restaurantDataService;
	public static UserManageDataService userManageDataService;
	public static UploadDataService uploadDataService;

	public static DataServiceFactory getInstance(String ip) {
		if (dataServiceFactory == null) {
			dataServiceFactory = new DataServiceFactory(ip);
		}
		return dataServiceFactory;
	}

	private DataServiceFactory(String ip) {
		try {

			blogDataService = (BlogDataService) Naming.lookup("rmi://" + ip
					+ ":8001/Blog");
			cityDataService = (CityDataService) Naming.lookup("rmi://" + ip
					+ ":8001/City");
			entertainmentDataService = (PlaceDataService) Naming
					.lookup("rmi://" + ip + ":8001/entertainment");
			hotelDataService = (PlaceDataService) Naming.lookup("rmi://" + ip
					+ ":8001/hotel");
			planDataService = (PlanDataService) Naming.lookup("rmi://" + ip
					+ ":8001/plan");
			recommandDataService = (RecommandDataService) Naming
					.lookup("rmi://" + ip + ":8001/recommand");
			restaurantDataService = (PlaceDataService) Naming
					.lookup("rmi://" + ip + ":8001/restaurant");
			userManageDataService = (UserManageDataService) Naming
					.lookup("rmi://" + ip + ":8001/userManage");

			landmarkDataService = (PlaceDataService) Naming.lookup("rmi://"
					+ ip + ":8001/Landmark");
			uploadDataService = (UploadDataService)Naming.lookup("rmi://"
					+ ip + ":8001/Landmark");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
