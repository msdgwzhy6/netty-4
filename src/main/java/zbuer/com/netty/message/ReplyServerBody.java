package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/28
 */
public class ReplyServerBody extends ReplyBody {
	private static final long serialVersionUID = -5260930284446425186L;
	private String serverInfo;

	public ReplyServerBody(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	public String getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}
}
