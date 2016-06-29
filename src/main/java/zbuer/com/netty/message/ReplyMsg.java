package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/28
 */
public class ReplyMsg extends BaseMsg {
	private static final long serialVersionUID = -9209723810237058945L;
	private ReplyBody replyBody;

	public ReplyBody getReplyBody() {
		return replyBody;
	}

	public void setReplyBody(ReplyBody replyBody) {
		this.replyBody = replyBody;
	}
}
