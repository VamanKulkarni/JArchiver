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
package archlib.tarlib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import archlib.AbstractArchiveOutputStream;
import archlib.ArchiveEntry;

/**
*
* @author Vaman Kulkarni
*/
public class TarFileOutputStream extends AbstractArchiveOutputStream {

    TarOutputStream tos = null;

    public TarFileOutputStream(OutputStream out) {
        tos = new TarOutputStream(out);
    }

    @Override
    public void putNextEntry(ArchiveEntry entry) throws IOException {
      tos.putNextEntry((TarEntry)entry);
    }

    @Override
    public <X extends InputStream> X getNativeOutputStream(Class<X> streamClass) {
        return streamClass.cast(tos);
    }

    public void padContent(TarEntry entry) throws IOException {
        tos.padContent(entry);
    }

    public void flush() throws IOException {
        tos.flush();
    }

    public void close() throws IOException {
        tos.close();
    }
    public void write(byte[] writeBuffer, int offset, int len) {
        tos.write(writeBuffer, offset, len);
    }



    public void putNextEntry(String entryName) throws Exception {
        putNextEntry(entryName);
    }
}
