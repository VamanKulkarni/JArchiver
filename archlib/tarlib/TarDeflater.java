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

public class TarDeflater extends AbstractArchiveDeflater {

    private static Logger log = CustomLogger.getLogger(TarDeflater.class);

    private static final int DATA_BUFFER_SIZE = 8092;

    private TarFileOutputStream tos = null;
    @Override
    public void deflate(String outputDir, String filePath) throws IOException {
        log.info("************** Started Deflate() ******************");
        initDeflater(outputDir, filePath, SupportedFileTypes.TAR.strFileType);

        this.tos = new TarFileOutputStream(new BufferedOutputStream(new FileOutputStream(resultResourceFile)));

        String pPath = inputResourceFile.isDirectory() ? inputResourceFile.getName() + "/" : "";

        if (inputResourceFile.isDirectory()) {
            TarEntry e = new TarEntry(inputResourceFile, "/");
            tos.putNextEntry(e);
            writeToFile(inputResourceFile, tos, pPath);
        } else {
            TarEntry e = new TarEntry(inputResourceFile, pPath);
            tos.putNextEntry(e);
            log.info("Writing data buffer for [" + e.getFileName() + "]");
            writeDataBuffer(inputResourceFile);
            tos.padContent(e);
        }
        tos.close();
        log.info("************** Finished deflate() ******************");

    }

    private void writeDataBuffer(File file) throws IOException {
        int readBytes = 0;
        byte dataBuffer[] = new byte[DATA_BUFFER_SIZE];

        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        while ((readBytes = fis.read(dataBuffer)) != -1) {
            tos.write(dataBuffer, 0, readBytes);
        }
        tos.flush();
        fis.close();
    }

    private void writeToFile(File parent, TarFileOutputStream tos, String ppath) throws IOException {
        TarEntry entry = null;
        File _file = null;
        File oldParent = null;
        String oldPath = null;

        String files[] = parent.list();
        for (String file : files) {
            _file = new File(parent, file);
            entry = new TarEntry(_file, ppath);
            log.info("Adding [" + entry.getFileName() + "]");
            tos.putNextEntry(entry);

            if (!_file.isDirectory()) {
                log.info("Writing data buffer for [" + entry.getFileName() + "]");
                writeDataBuffer(_file);
            } else {
                oldPath = ppath;
                ppath += file + "/";
                oldParent = parent;
                parent = _file;
                writeToFile(_file, tos, ppath);
                parent = oldParent;
                ppath = oldPath;
            }
            tos.padContent(entry);

        }
    }

    @Override
    public ArchiveOutputStream getArchiveOutputStream() {
        return tos;
    }

    @Override
    public void deflate(String srcFilePath) throws IOException {
        deflate(srcFilePath, null);
    }

}
