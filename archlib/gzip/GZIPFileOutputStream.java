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
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import archlib.ArchiveOutputStream;

/**
*
* @author Vaman Kulkarni
*/
public class GZIPFileOutputStream implements ArchiveOutputStream {

    private GZIPOutputStream gzOut = null;

    public GZIPFileOutputStream(OutputStream out) throws IOException {
        this.gzOut = new GZIPOutputStream(out);
    }

    @Override
    public <X extends InputStream> X getNativeOutputStream(Class<X> streamClass) {
        return streamClass.cast(gzOut);
    }

    public void write(byte[] buffer, int offset, int length) throws IOException {
        gzOut.write(buffer, offset, length);
    }

    public void close() throws IOException {
        gzOut.close();

    }

}
