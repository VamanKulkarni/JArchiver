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

package archlib.ziplib;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import archlib.AbstractArchiveInputStream;
import archlib.ArchiveEntry;
import archlib.util.CustomLogger;

/**
 * This class uses the {@link ZipInputStream} to extract a zip archive.
 * None of the methods from {@link ZipInputStream} are overridden in this
 * class. If you at all need to leverage the extra capabilities provided
 * by the {@link ZipInputStream} you can get its reference by <br/>
 * {@code ZipInputStream zis = inflater.getArchiveInputStream().getNativeInputStream(ZipInputStream.class);}
 * <br/>
 *
 *
 * @author Vaman Kulkarni
 *
 */
public class ZipFileInputStream extends AbstractArchiveInputStream{
    private Logger log = CustomLogger.getLogger(ZipFileInputStream.class);

	private ZipInputStream zis = null;

	public ZipFileInputStream(InputStream in) {
		zis = new ZipInputStream(in);
	}

	@Override
	public ArchiveEntry getNextEntry() throws IOException  {
		ZipEntry local = zis.getNextEntry();
		if(null != local)
			return new ZipFileEntry(local);
		log.warning("Returning a NULL entry");
		return null;
	}

	@Override
	public <X extends InputStream> X getNativeInputStream(Class<X> streamClass) {
	    return streamClass.cast(zis);
	}




}
