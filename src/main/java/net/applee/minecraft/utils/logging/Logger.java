package net.applee.minecraft.utils.logging;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.utils.Utils;
import net.applee.minecraft.utils.logging.exception.FolderCreateException;
import net.applee.minecraft.utils.logging.exception.LogCreateException;
import net.applee.minecraft.utils.string.AnsiColor;
import net.applee.minecraft.utils.string.ColoredPart;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logger {

	public static final File LOGS_FOLDER = new File("logs");

	private final BufferedWriter fileOut;

	public Logger() throws IOException {
		if (!LOGS_FOLDER.exists() && !LOGS_FOLDER.mkdirs())
			throw new FolderCreateException(LOGS_FOLDER);

		File logFile = new File(LOGS_FOLDER, getFileName());

		if (!logFile.exists() && !logFile.createNewFile())
			throw new LogCreateException(logFile);

		fileOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile)));
	}

	public void info(Object... args) {
		log(LogLevel.INFO, args);
	}

	public void warning(Object... args) {
		log(LogLevel.WARNING, args);
	}

	public void error(Object... args) {
		log(LogLevel.ERROR, args);
	}

	public void critical(Object... args) {
		log(LogLevel.CRITICAL, args);
	}

	public void exception(Exception e, Object... args) {
		log(LogLevel.ERROR, e.getClass().getSimpleName() + ":", e.getMessage(), args);
	}

	public void exception(LogLevel level, Exception e, Object... args) {
		log(level, e.getClass().getSimpleName() + ":", e.getMessage(), args);
	}

	public void stackTrace(LogLevel level, StackTraceElement[] stackTrace) {
		for (StackTraceElement element : stackTrace) log(level, element);
	}

	public void log(LogLevel level, Object... args) {

		ColoredPart time = ColoredPart.of(AnsiColor.PURPLE, "[%s]".formatted(getTime()));
		ColoredPart clas = ColoredPart.of(level.color, "[%s/%s]".formatted(getCallerClass(false), level));

		String formattedString = Utils.formatString(" ", "", "", false, args);
		formattedString = formattedString.replace("\n", " ");

		String outString = time + " " + clas + " " + formattedString;

		String logString = "[%s] ".formatted(getTime()) +
				"[%s/%s] ".formatted(getCallerClass(), level) + formattedString;

		logString = AnsiColor.removeColors(logString);

		System.out.println(outString);

		try {
			fileOut.write(logString);
			fileOut.newLine();
			fileOut.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private static String getFileName() {
		String fileName = LocalDate.now().toString();
		File[] logs = LOGS_FOLDER.listFiles();

		if (logs == null) return fileName + "-1.log";

		int counter = 1;

		for (File log : logs) {
			if (log.getName().equals("%s-%s.log".formatted(fileName, counter))) counter++;
			else break;
		}

		return "%s-%s.log".formatted(fileName, counter);
	}

	public static String getTime() {
		LocalTime time = LocalTime.now();
		return "%s:%s;%s".formatted(
				Utils.addZeros(time.getHour(), 2),
				Utils.addZeros(time.getMinute(), 2),
				Utils.addZeros(MinecraftClient.getInstance().tickCounter.get() % 100, 2)
		);
	}

	public static String getCallerClass() {
		return getCallerClass(true);
	}

	public static String getCallerClass(boolean method) {
		StackTraceElement[] tracer;
		tracer = new Throwable().getStackTrace();

		StackTraceElement callTrace = null;
		for (StackTraceElement trace : tracer) {
			if (trace.getClassName().equals(Logger.class.getName())) continue;
			callTrace = trace;
			break;
		}

		if (callTrace == null) return "LOG";

		String name = callTrace.getFileName();
		if (name == null) return "LOG";

		name = name.replace(".java", "");
		return name + (method ? "#" + callTrace.getMethodName() : "");
	}

	public enum LogLevel {
		INFO(AnsiColor.GREEN),
		WARNING(AnsiColor.YELLOW),
		ERROR(AnsiColor.RED),
		CRITICAL(AnsiColor.DARK_RED);

		public final AnsiColor color;

		LogLevel(AnsiColor color) {
			this.color = color;
		}
	}
}
