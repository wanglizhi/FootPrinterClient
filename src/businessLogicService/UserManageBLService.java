package businessLogicService;

import java.util.ArrayList;

import vo.LoginVO;
import vo.UserInforVO;

/**
 * 
 * @author SongShuo ���ܵ�½��ע�ᡢ�û��޸ĸ�����Ϣ�Ľӿ�
 */
public interface UserManageBLService {

	public boolean register(UserInforVO userInfor);

	public UserInforVO login(LoginVO loginInput);

	public boolean modifyUser(UserInforVO modifiedInput);// �ӽ����ȡ�޸��˵��û���Ϣ

	public ArrayList<UserInforVO> searchUser(String name); // �����û�
	
	public boolean exit(int userID);

}
