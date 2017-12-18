package ar.edu.davinci.exception;

public class AuthenticationFailure extends Exception{
	private static final long serialVersionUID = 2260054145693830712L;

	public AuthenticationFailure(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationFailure(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationFailure(String message) {
		super(message);
	}

	public AuthenticationFailure(Throwable cause) {
		super("Credenciales inválidas",cause);
	}

	
}
