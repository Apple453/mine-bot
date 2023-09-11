package net.applee.minecraft.utils.logging.exception;

import java.io.File;

public class LogCreateException extends RuntimeException {

	public LogCreateException(File file) {
		super("Can't create a logs file: %s".formatted(file.toString()));
	}
}
