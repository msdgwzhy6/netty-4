package zbuer.com.netty.message;

/**
 * @author buer
 * @since 16/6/28
 */
public class Constants {

	private static String clientId;

	public static String getClientId() {
		return clientId;
	}

	public static void setClientId(String clientId) {
		Constants.clientId = clientId;
	}
}
