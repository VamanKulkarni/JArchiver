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
package archlib;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import archlib.gzip.GZIPDeflater;
import archlib.tarlib.TarDeflater;
import archlib.util.CustomLogger;
import archlib.ziplib.ZipDeflater;

/**
 * Factory used to retrieve {@code ArchiveDeflater} depending on the input file
 * type supplied.
 * Client can deal with multiple file formats without having to change any of
 * their code since the underlying implementation is kept abstracted from the
 * user. Please refer below example.<br/>
 *
 * Example: <br/>
 *
 * <pre>
 * ArchiveDeflater	compressor	= ArchiveDeflaterFactory.getArchiveDeflater(SupportedFileTypes.ZIP);
 * </pre>

 * will return you the ArchiveDeflater specific to ZIP file (ZipInflater).
 *
 * @author Vaman Kulkarni
 *
 */

public class ArchiveDeflaterFactory {
	private static Logger	log	= CustomLogger.getLogger(ArchiveDeflaterFactory.class);

	/**
	 * Factory method to retrieve ArchiveDeflater instance depending on the type
	 * requested.
	 *
	 * @param file
	 *            Type of the supplied file
	 * @return {@code ArchiveDeflater} to work with given file type
	 * @throws FileNotFoundException
	 */
	public static ArchiveDeflater getArchiveDeflater(SupportedFileTypes file) throws FileNotFoundException {
		return getArchiveDeflater(file.strFileType);
	}

	public static ArchiveDeflater getArchiveDeflater(String defalterName) throws FileNotFoundException {
	    log.info("Requested Deflator = " + defalterName);
	    if (defalterName.equalsIgnoreCase(SupportedFileTypes.TAR.strFileType)) {
            return new TarDeflater();
        }
	    if (defalterName.equalsIgnoreCase(SupportedFileTypes.ZIP.strFileType)){
            return new ZipDeflater();
        }
	    if (defalterName.equalsIgnoreCase(SupportedFileTypes.GZIP.strFileType)){
            return new GZIPDeflater();
        }
	    return null;

	}
}
