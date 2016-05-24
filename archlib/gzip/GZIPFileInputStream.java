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
package archlib.gzip;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import archlib.ArchiveInputStream;

/**
 * @author Vaman Kulkarni
 *
 */
public class GZIPFileInputStream implements ArchiveInputStream {

	private GZIPInputStream gzInstream = null;

	public GZIPFileInputStream(InputStream in) throws IOException {
		this.gzInstream = new GZIPInputStream(in);
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see archlib.GenericFileInputStream#getNativeInputStream(Class<X>)
	 */
	@Override
	public <X extends InputStream> X getNativeInputStream(Class<X> streamClass) {
		return streamClass.cast(gzInstream);
	}


    public int read(byte[] buffer) throws IOException {
        return this.gzInstream.read(buffer);
    }


    public void close() throws IOException {
        gzInstream.close();
    }

}
