package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/29
 */
public class AskMsg extends BaseMsg{

	private static final long serialVersionUID = 7722307360412226676L;

	public AskMsg() {
		super();
		setType(MsgType.ASK);
	}
	private AskParams params;

	public AskParams getParams() {
		return params;
	}

	public void setParams(AskParams params) {
		this.params = params;
	}
}
