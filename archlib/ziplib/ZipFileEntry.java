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

import java.io.File;
import java.util.zip.ZipEntry;

import archlib.ArchiveEntry;

/**
 * Provides useful wrapper over {@link java.util.ZipEntry} in order to make it
 * compatible with the <code>ArchiveEntry</code> APIs.
 *
 * @author Vaman Kulkarni
 *
 */
public class ZipFileEntry implements ArchiveEntry {
	/**
     * Encapsulated ZipEntry
     * @uml.property  name="zEntry"
     */
	private ZipEntry zEntry = null;

	/**
	 * Creates a ZipFileEntry with the given name.
	 * @param name Name of the entry to be created.
	 */
	public ZipFileEntry(String name) {
		zEntry = new ZipEntry(name);
	}

	public ZipFileEntry(File _file){
	    this(_file.getName());
	}
	/**
	 * Creates a ZipFileEntry with the give ZipEntry.
	 * @param _entry
	 */
	public ZipFileEntry(ZipEntry _entry){
		this.zEntry = _entry;
	}
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return zEntry.getName();
	}

	@Override
	public long getFileSize() {
		// TODO Auto-generated method stub
		return zEntry.getSize();
	}

	@Override
	public long getModificationTime() {
		// TODO Auto-generated method stub
		return zEntry.getTime();
	}


	@Override
	public void setFileSize(long size) {
		zEntry.setSize(size);
	}

	@Override
	public void setModificationTime(long time) {
		zEntry.setTime(time);
	}

	@Override
	public boolean isDirectory() {
		return zEntry.isDirectory();
	}

	@Override
	public Object getNativeEntry() {
		// TODO Auto-generated method stub
		return zEntry;
	}

}
