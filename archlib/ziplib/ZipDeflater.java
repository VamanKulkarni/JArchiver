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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import archlib.AbstractArchiveDeflater;
import archlib.ArchiveOutputStream;
import archlib.SupportedFileTypes;
import archlib.util.CustomLogger;

/**
* Deflater for ZIP files
* @author Vaman Kulkarni
*/
public class ZipDeflater extends AbstractArchiveDeflater{

    private static final int DATA_BUFFER_SIZE = 1024;

    private Logger log = CustomLogger.getLogger(ZipDeflater.class);

    private ZipFileOutputStream zis = null;

    @Override
    public void deflate(String srcPath, String destAbsFilePath) throws IOException {
        log.info("************** Started Deflate() ******************");
        initDeflater(srcPath, destAbsFilePath, SupportedFileTypes.ZIP.strFileType);
        this.zis = new ZipFileOutputStream(new BufferedOutputStream(new FileOutputStream(resultResourceFile)));

        if (inputResourceFile.isDirectory()) {
            int len = inputResourceFile.getAbsolutePath().lastIndexOf(File.separator);
            String baseName = inputResourceFile.getAbsolutePath().substring(0, len + 1);
            addFolderToZip(inputResourceFile, zis, baseName);
        } else {
            ZipFileEntry entry = new ZipFileEntry(inputResourceFile);
            zis.putNextEntry(entry);
            writeDataBuffer(inputResourceFile);
            zis.flush();
            zis.closeEntry();
        }
        zis.close();
        log.info("************** Finished deflate() ******************");
    }

    private void writeDataBuffer(File file) throws IOException {
        int readBytes = 0;
        byte dataBuffer[] = new byte[DATA_BUFFER_SIZE];

       InputStream fis = new BufferedInputStream(new FileInputStream(file));
        while ((readBytes = fis.read(dataBuffer)) != -1) {
            zis.write(dataBuffer, 0, readBytes);
        }
        zis.flush();
        fis.close();
    }

    private void addFolderToZip(File folder, ZipFileOutputStream zip, String baseName) throws IOException {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                log.info("Adding ["+file.getName()+"]");
                addFolderToZip(file, zip, baseName);
            } else {
                String name = file.getAbsolutePath().substring(baseName.length());
                ZipFileEntry zipEntry = new ZipFileEntry(name);
                log.info("Adding ["+zipEntry.getFileName()+"]");
                zip.putNextEntry(zipEntry);
                writeDataBuffer(file);
                zip.closeEntry();

            }
        }
    }

    @Override
    public ArchiveOutputStream getArchiveOutputStream() {
        return zis;
    }

    @Override
    public void deflate(String srcFilePath) throws IOException {
        deflate(srcFilePath, null);
    }

}
