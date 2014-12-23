package de.scouthero.util;

import org.apache.log4j.Logger;

public class LogUtil {
	
	/**
	 * 
	 * @param logger
	 * @param methodName
	 */
	public static void debugEnter(final Logger logger, final String methodName) {
		debugEnter(logger, methodName, null);
	}
	
	/**
	 * 
	 * @param logger
	 * @param methodName
	 */
	public static void debugExit(final Logger logger, final String methodName) {
		debugExit(logger, methodName, null);
	}
			
	/**
	 * 
	 * @param logger
	 * @param methodName
	 * @param param
	 * @param objects
	 */
	public static void debugEnter(final Logger logger, final String methodName, final String param, Object... objects) {
		StringBuilder builder = new StringBuilder("--> entering "+methodName);
		if (param != null) {
			builder.append(" "+param);
			if (objects != null) {
				for (int i = 0; i < objects.length; i++) {
					builder.append(" "+objects[i].toString());
				}
			}
		}
		debug(logger, builder);
	}
	
	/**
	 * 
	 * @param logger
	 * @param methodName
	 * @param param
	 * @param objects
	 */
	public static void debugExit(final Logger logger, final String methodName, final String param, Object... objects) {
		StringBuilder builder = new StringBuilder("<-- exiting "+methodName);
		if (param != null) {
			builder.append(" "+param);
			if (objects != null) {
				for (int i = 0; i < objects.length; i++) {
					if (objects[i] != null)
						builder.append(" "+objects[i].toString());
				}
			}
		}
		debug(logger, builder);
	}

	/**
	 * 
	 * @param logger 
	 * @param builder
	 */
	private static void debug(Logger logger, StringBuilder builder) {
		logger.info(builder);
	}
}
