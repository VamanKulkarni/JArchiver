/*
 * ====================================================================================
 * JArchiver: A simple library to compress and decompress archives of multiple formats.
 * ====================================================================================
 *
 * Copyright (C) 2011  Vaman Kulkarni
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package archlib.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A custom logger which reads configuration from external xml file.
 *
 * @author Vaman Kulkarni
 *
 */
public class CustomLogger {

	private static Handler		fileHandler		= null;
	private static Handler		consoleHandler	= null;
	private static Formatter	formatter		= null;

	public static Logger getLogger(Class klass) {
		if (consoleHandler == null) {
			consoleHandler = new ConsoleHandler();
		}
		if (formatter == null) {
			formatter = new CustomFormatter();
		}
		consoleHandler.setFormatter(formatter);
		Logger _logger = Logger.getLogger(klass.getName());
		_logger.setLevel(Level.FINEST);
		_logger.setUseParentHandlers(false);
		_logger.addHandler(consoleHandler);
		return _logger;
	}

}

class CustomFormatter extends Formatter {

	private static final SimpleDateFormat	sdf	= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

	@Override
	public String format(LogRecord record) {
		StringBuffer recBuff = new StringBuffer(1000);
		recBuff.append(sdf.format(new Date(record.getMillis()))).append(" - ");
		recBuff.append("[").append(record.getSourceClassName()).append(".").append(record.getSourceMethodName())
				.append("] - ");
		recBuff.append("[").append(record.getLevel()).append("] - ");
		recBuff.append(formatMessage(record)).append("\n");
		return recBuff.toString();
	}

}
