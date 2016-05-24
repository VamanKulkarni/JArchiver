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

import java.io.IOException;

/**
 * Provides simple interface for client to extract files from an archive. It is a parent interface for all types of inflaters that are supported by this library. Currently <code>ArchiveInflater</code> can be used to deal with <code>TAR, ZIP, GZIP</code> archives. <br/> It allows user to <b>inflate</b> any type of file without having knowledge of their concrete implementation.<br/>  {@link ArchiveInflater}  can be retrieved from {@link ArchiveInflaterFactory}  using method {@link ArchiveInflaterFactory#getArchiveInflater(SupportedFileTypes)}  <br/> off the archive.<br/> <br/> <b>Example Usage:</b> <pre> ArchiveInflater inflater = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.TAR); inflater.inflate(&quot;d:/archives/file.tar&quot;, &quot;d:/archives/extracted&quot;); </pre> <pre> ArchiveInflater inflater = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.ZIP); inflater.inflate(&quot;d:/archives/file.tar&quot;); </pre> will inflate/extract provided file to the directory named <code>file</code>
 * @author  Vaman Kulkarni
 */
public interface ArchiveInflater {

	/**
	 * Method used to extract data from supplied archive file.
	 * E.g. unzip, untar, ungzip in other words decompress.
	 *
	 * @param outputDir
	 *            Output directory where all the files will be extracted.
	 * @throws Exception
	 */
	public void inflate(String archiveFilePath, String outputDir) throws IOException;

	/**
	 * Extracts data from the previously set archive file.
	 * Before calling this version of method you must set
	 * archive name and the output location using their
	 * corrosponding setter methods.
	 *
	 * @throws IOException
	 */
	public void inflate() throws IOException;

	/**
	 * Inflates archive to the supplied archive's parent directory.<br/>
	 * <b>Example:</b><br/>
	 * <br/>
	 * <code>
	 * inflate("d:/test/sample.zip")
	 * </code> will extract sample.zip to location <b>d:/test</b>
	 *
	 * @param achiveFilePath
	 *            path to the archive
	 * @throws IOException
	 */
	public void inflate(String achiveFilePath) throws IOException;

	/**
     * Sets the archive file name which will be inflated (extracted) by  {@code  this}  inflater.
     * @param  archiveFilePath
     */
	public void setArchiveFileName(String archiveFilePath);

	/**
     * Returns you the archive file name associated with  {@code  this}  inflater.
     * @return  String Archive file name
     */
	public String getArchiveFileName();

	/**
     * Sets the output directory location where will  {@code  this}  inflater will extract archive.
     * @param  archiveFilePath
     */
	public void setOutputLocation(String outputDir);

	/**
     * Returns you the output directory location  {@code  this}  inflater will/has extrac(ed) files.
     * @return  String Output directory location
     */
	public String getOutputLocation();

	/**
	 * Must be called after inflate() otherwise retuns NULL<br/>
	 * If inflated resource is a directory then it returns the absolute path of inflated directory.
	 * If inflated resource is a file then it return the file name. <br/>
	 *
	 * For e.g. <br/>
	 * 1)<br/><br/>
	 * Assume input = /home/Vaman Kulkarni/test_data/hello.zip contains hello.txt
	 * <pre>
	 * inflater.inflate(input);
	 * inflater.getInflatedResource() will return /home/Vaman Kulkarni/test_data/hello.txt
	 * </pre>
	 * 2)<br/><br/>
     * Assume input = /home/Vaman Kulkarni/test_data/folder.zip contains a directory named test_dir <br/>
     * <pre>
     * inflater.inflate(input);
     * inflater.getInflatedResource() will return /home/Vaman Kulkarni/test_data/test_dir
     * </pre>
	 *
	 *
	 * @return String
	 */
	public String getInflatedResource();

	/**
	 * Returns the ArchiveInputStream used by {@code this} Inflater.
	 * @return
	 */
	public ArchiveInputStream getArchiveInputStream();
}
