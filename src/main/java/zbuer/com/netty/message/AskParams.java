package zbuer.com.netty.message;

import java.io.Serializable;

/**
 * @author buer
 * @since 16/6/29
 */
public class AskParams implements Serializable {

	private static final long serialVersionUID = -7693350028779287507L;

	private String auth;

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}
