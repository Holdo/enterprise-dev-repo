package cz.muni.fi.pb138.webmvc.websocket.exception;

/**
 * Created by Michal Holic on 15/06/2016
 */
public class UnknownWebSocketCommandException extends Exception {

	public UnknownWebSocketCommandException() {
		super();
	}

	public UnknownWebSocketCommandException(String message) {
		super(message);
	}

	public UnknownWebSocketCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownWebSocketCommandException(Throwable cause) {
		super(cause);
	}

	protected UnknownWebSocketCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
