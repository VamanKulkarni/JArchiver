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
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import archlib.AbstractArchiveOutputStream;
import archlib.ArchiveEntry;

/**
 * OutputStreams which handles Zip format. It encapsulates {@link ZipOutputStream}
 * @author Vaman Kulkarni
 */
public class ZipFileOutputStream extends AbstractArchiveOutputStream {

    private ZipOutputStream zos = null;

    public ZipFileOutputStream(OutputStream os) {
        zos = new ZipOutputStream(os);

    }

    @Override
    public void putNextEntry(ArchiveEntry entry) throws IOException {
        zos.putNextEntry(new ZipEntry(entry.getFileName()));
    }


    @Override
    public  <X extends InputStream> X getNativeOutputStream(Class<X> streamClass) {
        return streamClass.cast(zos);
    }

    public void write(byte[] dataBuffer, int offset, int len) throws IOException {
        zos.write(dataBuffer, offset, len);
    }
    public void flush() throws IOException {
        zos.flush();
    }
    public void close() throws IOException {
        zos.close();
    }
    public void closeEntry() throws IOException {
        zos.closeEntry();
    }


}
