package net.applee.minecraft.utils.logging.exception;

import java.io.File;

public class FolderCreateException extends RuntimeException {
	public FolderCreateException(File file) {
		super("Can't create a logs folder: %s".formatted(file.toString()));
	}
}
