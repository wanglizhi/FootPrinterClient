package startup;

import tools.Config;
import businessLogicService.*;
import businessLogicService_imp.*;

/**
 * 
 * @author SongShuo 生成 dataserviceFactory 和所有的BLService
 * 
 */
public class BLServiceFactory {

	private static BLServiceFactory blServiceFactory;

	//
	public BlogBLService blogBLService;
	public BlogHomeBLService blogHomeBLService;
	public CityBLService cityBLService;
	public PlaceBLService landmarkBLService;
	public PlanBLService planBLService;
	public PlanHomeBLService planHomeBLService;
	public RecommandBLService recommandBLService;
	public SpaceBLService spaceBLService;
	public UserManageBLService userManageBLService;
	public PlaceBLService entertainmentBLService;
	public PlaceBLService hotelBLService;
	public PlaceBLService marketBLSetvice;
	public PlaceBLService restaurantBLService;
	//

	public static BLServiceFactory getInstance() {
		if (blServiceFactory == null) {
			blServiceFactory = new BLServiceFactory();
		}
		return blServiceFactory;
	}

	private BLServiceFactory() {

//		blogHomeBLService = new BlogHomeBLService_imp(
//				ClientNetworkInit.blogDataService);
//		planHomeBLService = new PlanHomeBLService_imp(
//				ClientNetworkInit.planDataService);
//		
//		recommandBLService = new RecommandBLService_imp(
//				ClientNetworkInit.recommandDataService);
//		spaceBLService = new SpaceBLService_imp(
//				ClientNetworkInit.planDataService,
//				ClientNetworkInit.blogDataService);
//		userManageBLService = new UserManageBLService_imp(
//				ClientNetworkInit.userManageDataService);
		
		blogHomeBLService = new BlogHomeBLService_imp(
				ClientNetworkInit.blogDataService);
		planHomeBLService = new PlanHomeBLService_imp(
				ClientNetworkInit.planDataService);
		
		recommandBLService = new RecommandBLService_imp(
				null);
		spaceBLService = new SpaceBLService_imp(
				ClientNetworkInit.planDataService,
				ClientNetworkInit.blogDataService);
		userManageBLService = new UserManageBLService_imp(
				ClientNetworkInit.userManageDataService);
		planBLService = new PlanBLService_imp();
		
		//wanglizhi
		landmarkBLService = new LandmarkBLService_imp(ClientNetworkInit.landmarkDataService);
		entertainmentBLService = new EntertainmentBLService_imp(ClientNetworkInit.entertainmentDataService);
		hotelBLService = new HotelBLService_imp(ClientNetworkInit.hotelDataService);
		marketBLSetvice = new MarketBLService(ClientNetworkInit.marketDataService);
		restaurantBLService = new RestaurantBLService_imp(ClientNetworkInit.restaurantDataService);
		blogBLService = new BlogBLService_imp(Config.IP,ClientNetworkInit.uploadDataService,ClientNetworkInit.blogDataService);
		cityBLService =new CityBLService_imp(ClientNetworkInit.cityDataService);
		
		//ss
		userManageBLService = new UserManageBLService_imp(ClientNetworkInit.userManageDataService);
		recommandBLService = new RecommandBLService_imp(ClientNetworkInit.recommandDataService);
		
		
	}

}
