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

import java.io.File;
import java.io.IOException;

import archlib.ArchiveEntry;

/**
 * Class representing an entry in the archive.
 * @author Vaman Kulkarni
 *
 */
public class TarEntry implements ArchiveEntry {

	private String				fileName;
	private int					fileSize;

	private static final byte	NORMAL_FILE	= 0;
	private static final byte	DIRECTORY	= 5;

	private TarHeader			header;

	public TarEntry(byte[] buff) throws IOException {
		this.header = new TarHeader(buff);
	}

	public TarEntry(File target, String parentPath) {
		this.header = new TarHeader(target, parentPath);

	}

	/**
     * @return
     */
	public TarHeader getHeader() {
		return this.header;
	}

	@Override
	public String getFileName() {
		return header.getFileName();
	}

	@Override
	public long getFileSize() {
		return header.getFileSize();
	}


	@Override
	public boolean isDirectory() {
		byte b = this.header.getLinkIndicator();
		switch (b) {
		case NORMAL_FILE:
			return false;
		case DIRECTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public long getModificationTime() {
		return this.header.getLastModifiedTimeStamp();
	}

	@Override
	public void setFileSize(long size) {
	    this.header.setFileSize(size);
	}

	@Override
	public void setModificationTime(long time) {
	    this.header.setLastModifiedTimeStamp(time);
	}

	@Override
	public Object getNativeEntry() {
		return this;
	}
}
