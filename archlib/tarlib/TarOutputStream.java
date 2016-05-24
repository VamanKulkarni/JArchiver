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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
*
* @author Vaman Kulkarni
*/
public class TarOutputStream extends FilterOutputStream{

	private int					bytesWritten		= 0;
	private TarEntry			currentEntry		= null;
	private static final int	END_SEQUENCE_LEN	= 1024;
	private static final byte[]	END_SEQUENCE		= new byte[END_SEQUENCE_LEN];

	public TarOutputStream(OutputStream out) {
		super(out);

	}

	public void putNextEntry(TarEntry entry) throws IOException {

		currentEntry = entry;
		writeEntry(entry);

	}

	public void putNextEntry(TarEntry entry, byte[] large) throws IOException {
		currentEntry = entry;
		writeEntry(entry);

	}

	public void writeEntryToLargeBuffer(TarEntry entry, byte[] large) throws IOException {
		// write header
		byte[] header = entry.getHeader().getBuffer();

		writeToLargeBuffer(large, header, bytesWritten, header.length);
		write(header);
	}

	public void writeEntry(TarEntry entry) throws IOException {
		// write header
		byte[] header = entry.getHeader().getBuffer();
		write(header);


	}


	public void writeLargeBufferToFile(byte[] writeBuffer, int offset, int len) {
		try {
			super.write(writeBuffer, bytesWritten, len);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(byte[] writeBuffer, int offset, int len) {
		bytesWritten += offset;
		try {
			super.write(writeBuffer, bytesWritten, len);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToLargeBuffer(byte[] largeBuffer, byte[] writeBuffer, int offset, int len) {
		bytesWritten += offset;
		try {
			System.arraycopy(writeBuffer, offset, largeBuffer, bytesWritten, len);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long getPaddingByteSize(TarEntry entry) {
		long fileSize = entry.getFileSize();
		long possiblePadding = fileSize % TarLibConstants.TAR_HEADER_SIZE;
		if (possiblePadding > 0) {
			return TarLibConstants.TAR_HEADER_SIZE - possiblePadding;
		}
		return possiblePadding;
	}

	public void padContent(TarEntry entry) throws IOException {
		long padding = getPaddingByteSize(entry);

		if (padding > 0) {
			byte[] padBuffer = new byte[(int) padding];
			write(padBuffer);
		}
	}

	@Override
	public void close() throws IOException {
		write(END_SEQUENCE, bytesWritten, END_SEQUENCE_LEN);
		super.close();
	}

	public int getBytesWritten() {
		return bytesWritten;
	}



}
