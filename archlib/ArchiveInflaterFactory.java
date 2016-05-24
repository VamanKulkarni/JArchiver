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

import archlib.gzip.GZIPInflater;
import archlib.tarlib.TarInflater;
import archlib.util.CustomLogger;
import archlib.ziplib.ZipInflater;

/**
 * Factory used to retrieve {@code ArchiveInflater} depending on the input file
 * type supplied.
 * Client can deal with multiple file formats without having to change any of
 * their code since the underlying implementation is kept abstracted from the
 * user. Please see below examples.<br/>
 *
 * Example: <br/>
 *
 * <pre>
 * ArchiveInflater	decompressor	= ArchiveFactory.getArchiveInflater(SupportedFileTypes.TAR); *
 * </pre>
 *
 * will return you the ArchiveInflater specific to TAR file (TarInflater).
 *
 * @author Vaman Kulkarni
 *
 */
public class ArchiveInflaterFactory {
	private static Logger	log	= CustomLogger.getLogger(ArchiveInflaterFactory.class);

	/**
	 * Factory method to retrieve ArchiveInflater instance depending on the type
	 * requested.
	 *
	 * @param file
	 *            Type of the supplied file
	 * @return {@code ArchiveInflater} to work with given file type
	 * @throws FileNotFoundException
	 */
	public static ArchiveInflater getArchiveInflater(SupportedFileTypes file) throws FileNotFoundException {
		if (file == SupportedFileTypes.TAR) {
			return new TarInflater();
		}
		if (file == SupportedFileTypes.ZIP) {
			return new ZipInflater();
		}
		if (file == SupportedFileTypes.GZIP) {
			return new GZIPInflater();
		}
		return null;
	}

	public static ArchiveInflater getArchiveInflater(String infName) throws FileNotFoundException {
        log.info("Requested Inflater = " + infName);
        if (infName.equalsIgnoreCase(SupportedFileTypes.TAR.strFileType)) {
            return new TarInflater();
        }
        if (infName.equalsIgnoreCase(SupportedFileTypes.ZIP.strFileType)){
            return new ZipInflater();
        }
        if (infName.equalsIgnoreCase(SupportedFileTypes.GZIP.strFileType)){
            return new GZIPInflater();
        }
        return null;
    }
}
