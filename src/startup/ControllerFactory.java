package startup;

import tools.Debugger;
import controller.*;

/**
 * @author SongShuo 生成BLServiceFactory 以及所有的controller
 * */
public class ControllerFactory {
	private BLServiceFactory blServiceFactory;
	private static ControllerFactory controllerFactory = null;

	public BlogController blogController;
	public BlogShowController blogShowController;
	public BlogHomeController blogHomeController;
	public HomepageController homepageController;
	public LandmarkController landmarkController;
	public UserManageController loginRegisterController;
	public PlanController planController;
	public PlanHomeController planHomeController;
	public SpaceController spaceController;
	public CheckPlanController checkPlanController;
	public UserManageController userManageController;

	public static ControllerFactory getInstance() {
		if (controllerFactory == null) {
			controllerFactory = new ControllerFactory();

		}
		return controllerFactory;
	}

	private ControllerFactory() {
		blServiceFactory = BLServiceFactory.getInstance();

		homepageController = new HomepageController(
				blServiceFactory.userManageBLService);
		Debugger.log("finish loading homepageController");
		blogHomeController = new BlogHomeController(
				blServiceFactory.blogHomeBLService);
		Debugger.log("finish loading bloghomeController");
		landmarkController = new LandmarkController(blServiceFactory);
		Debugger.log("finish loading landmarkController");
		loginRegisterController = new UserManageController(
				blServiceFactory.userManageBLService);
		Debugger.log("finish loading loginController");
		planController = new PlanController(blServiceFactory.planBLService);
		Debugger.log("finish loading planController");
		planHomeController = new PlanHomeController(
				blServiceFactory.planHomeBLService);
		Debugger.log("finish loading planHomeController");
		spaceController = new SpaceController(blServiceFactory.spaceBLService);
		Debugger.log("finish loading spaceController");
		checkPlanController = new CheckPlanController(
				blServiceFactory.planBLService);
		Debugger.log("finish loading checkPlanController");
		blogController = new BlogController(blServiceFactory.blogBLService);
		Debugger.log("finish loading blogController");

		userManageController = new UserManageController(BLServiceFactory.getInstance().userManageBLService);
		Debugger.log("finish loading userManageController");
		blogShowController = new BlogShowController(blServiceFactory.blogBLService);
		Debugger.log("finish loading blogShowController");
	}
}
