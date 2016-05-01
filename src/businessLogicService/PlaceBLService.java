package businessLogicService;

import java.util.ArrayList;

import vo.PlaceVO;

public interface PlaceBLService {
	public PlaceVO showPlace(int placeID);

	public boolean addApplause(int userID, int placeID);

	public boolean deleteApplause(int userID, int placeID);

	public boolean checkIsApplaused(int userID, int placeID);

	public ArrayList<PlaceVO> searchByCityID(int cityID);
	
	public ArrayList<PlaceVO> searchByCityID(int cityID,int size);
	
	public ArrayList<PlaceVO> searchByTag(ArrayList<String> tags);
	 
	public ArrayList<PlaceVO> search(String key);
}
