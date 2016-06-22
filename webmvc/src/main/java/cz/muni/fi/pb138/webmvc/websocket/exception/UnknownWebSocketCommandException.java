package cz.muni.fi.pb138.webmvc.websocket.exception;

/**
 * @author Michal Holič
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
