package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/28
 */
public class PingMsg extends BaseMsg {

	private static final long serialVersionUID = 5088195529308606085L;

	public PingMsg() {
		setType(MsgType.PING);
	}
}
