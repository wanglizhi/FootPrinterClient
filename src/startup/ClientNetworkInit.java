package startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import dataService.BlogDataService;
import dataService.CityDataService;
import dataService.PlaceDataService;
import dataService.PlanDataService;
import dataService.RecommandDataService;
import dataService.UploadDataService;
import dataService.UserManageDataService;

public class ClientNetworkInit {

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

	public ClientNetworkInit(String ip) {
		try {

			landmarkDataService = (PlaceDataService) Naming.lookup("rmi://"
					+ ip + ":8012/Landmark");
			cityDataService = (CityDataService) Naming.lookup("rmi://" + ip
					+ ":8002/City");
			hotelDataService = (PlaceDataService) Naming.lookup("rmi://" + ip
					+ ":8003/Hotel");
			restaurantDataService = (PlaceDataService) Naming
					.lookup("rmi://" + ip + ":8004/Restaurant");
			entertainmentDataService = (PlaceDataService) Naming
					.lookup("rmi://" + ip + ":8005/Entertainment");
			marketDataService=(PlaceDataService)Naming.lookup("rmi://"+ip+":8006/Market");
			userManageDataService = (UserManageDataService) Naming
					.lookup("rmi://" + ip + ":8007/User");
			planDataService = (PlanDataService) Naming.lookup("rmi://" + ip
					+ ":8008/Plan");
			blogDataService = (BlogDataService) Naming.lookup("rmi://" + ip
					+ ":8009/Blog");
			recommandDataService = (RecommandDataService) Naming
					.lookup("rmi://" + ip + ":8010/Recommand");
			
			uploadDataService = (UploadDataService)Naming.lookup("rmi://"
					+ ip + ":8011/Upload");
			

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
