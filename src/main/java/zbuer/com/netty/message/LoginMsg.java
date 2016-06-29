package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/28
 */
public class LoginMsg extends  BaseMsg{

	private static final long serialVersionUID = 3259238400615483055L;

	private String userName;

	private String password;

	public LoginMsg() {
		super();
		setType(MsgType.LOGIN);

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
