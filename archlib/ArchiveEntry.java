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

/**
 * @author  Vaman Kulkarni
 */
public interface ArchiveEntry {

	/**
     * Returns the entry name
     * @return
     * @uml.property  name="fileName"
     */
	public String getFileName();

	/**
	 * Returns the file size represented by this entry.
	 *
	 * @return
	 */
	public long getFileSize();

	/**
	 * Returns true if the file represented by this entry is a directory
	 * otherwise returns false.
	 *
	 * @return
	 */
	public boolean isDirectory();

	/**
	 * Returns the Last Modification time of the file represented by this entry.
	 *
	 * @return
	 */
	public long getModificationTime();

	public void setFileSize(long size);

	public void setModificationTime(long time);

	/**
     * Returns the underlying Entry of the file. Should be used if you need access to the file specific entry methods. E.g.<br/> <code>ZipEntry entry = (ZipEntry)is.getNextEntry().getNativeEntry();
     * @return
     * @uml.property  name="nativeEntry"
     */
	public Object getNativeEntry();
}
