package zbuer.com.netty.message;

import java.io.Serializable;

/**
 * @author buer
 * @since 16/6/28
 */
public class BaseMsg implements Serializable {

	private static final long serialVersionUID = -7439476011971541512L;

	private MsgType type;

	private String clientId;

	public BaseMsg() {
		this.clientId = Constants.getClientId();
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
