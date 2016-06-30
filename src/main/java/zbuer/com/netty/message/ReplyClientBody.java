package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/28
 */
public class ReplyClientBody extends  ReplyBody {



	private String clientInfo;

	public String getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}

	public ReplyClientBody(String clientInfo) {
		this.clientInfo = clientInfo;
	}
}
