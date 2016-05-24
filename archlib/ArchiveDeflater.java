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
 * Provides simple interface for clients to write the files to an archive. A parent interface for all types of deflaters
 * that are supported by this library. <code>ArchiveDeflater</code> can be used to deal with any type of archive E.g.
 * <code>TAR, ZIP, GZIP</code> etc.<br/>
 * It allows user to <b>deflate</b> any type of file without having knowledge of their concrete implementation. It has a
 * overloaded versions of generic {@code  deflate()} method which can be used write data in an archive.<br/>
 * <br/>
 * <b>Example Usage:</b>
 *
 * <pre>
 * ArchiveDeflater deflater = ArchiveDeflaterFactory.getArchiveDeflater(SupportedFileTypes.TAR);
 * deflater.deflate(&quot;d:/file.txt&quot;, &quot;d:/archives/file.tar&quot;);
 * </pre>
 *
 * @author Vaman Kulkarni
 */

public interface ArchiveDeflater {

    /**
     * Method used to write {@code srcPath} file(s) into an archive referenced by {@code destAbsFilePath}.
     *
     * @param srcPath
     *            Path of the file or directory which you want to write to an archive.
     * @param destAbsFilePath
     *            Name of the output archive file.
     *
     */
    public void deflate(String srcPath, String destAbsFilePath) throws IOException; // Compress, archive

    /**
     * Deflates the given source path to file with the same name as that of the resource referenced by the source path. <br/>
     * <b>Example:</b><br/>
     * Consider deflater used in below example is a TAR deflater.<br/>
     * <br/>
     * <code>
     *     deflate("d:/test/toCompress")
     * </code><br/>
     * <br/>
     * will deflate toCompress directory to <b>d:/test/toCompress.tar</b><br/>
     * <br/>
     * OR<br/>
     * <br/>
     * <code>
     *     deflate("d:/test/tarme.txt")
     * </code><br/>
     * <br/>
     * will deflate tarme.txt file to <b>d:/test/tarme.txt.tar</b><br/>
     *
     * @param srcFilePath
     *            path to the input resource
     * @throws IOException
     */
    public void deflate(String srcFilePath) throws IOException;

    /**
     * Returns the resultant archive file name.<br/>
     * <b>Note:</b> This is not a check for file deflation as this file name is derived before deflate.
     * @return
     */
    public String getDeflatedFileName();

    /**
     * Returns the ArchiveOutpuStream used by {@code this} Deflater.
     * @return ArchiveOutputStream
     */
    public ArchiveOutputStream getArchiveOutputStream();

}
