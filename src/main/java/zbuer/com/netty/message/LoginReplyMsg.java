package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/29
 */
public class LoginReplyMsg extends BaseMsg{
	private static final long serialVersionUID = 5331823676424274506L;

	private String loginToken;

	private boolean success = false;

	public LoginReplyMsg() {
		super();
		setType(MsgType.LOGIN_REPLY);
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
