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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;

import archlib.AbstractArchiveInflater;
import archlib.ArchiveInputStream;
import archlib.util.CustomLogger;

/**
 * Inflater for ZIP files
 * @author Vaman Kulkarni
 */
public class ZipInflater extends AbstractArchiveInflater {

    private Logger log = CustomLogger.getLogger(ZipInflater.class);

    private ZipFileInputStream zis = null;


    public ZipInflater(String filePath) throws IOException {
        initInflater(filePath,null);
    }

    public ZipInflater() {
    }


    @Override
    public void inflate() throws IOException {
        inflate(this.inputArchiveResourceStr, this.outputResourceStr);
    }

    @Override
    public void inflate(String archiveFilePath, String outputDir) throws  IOException  {
        log.info("inflate >>>");
        initInflater(archiveFilePath,outputDir);
        this.zis = new ZipFileInputStream(new FileInputStream(inputArchiveResourceFile));

        if (!outputResourceFile.exists()) {
            log.info(outputResourceFile.getName()
                    + " does not exists hence creating a new one");
            outputResourceFile.mkdir();
        }

        ZipFileEntry entry = null;
        boolean isCorrupt = true;
        log.info("Start parsing entries from the compressed file");
        while ((entry = (ZipFileEntry) zis.getNextEntry()) != null) {
            log.info("Extracting entry [" + entry.getFileName() + "] ");
            isCorrupt = false;
            File extracted = null;
            if (entry.isDirectory()) {
                File _extracted = new File(outputResourceFile, "/" + entry.getFileName());
                _extracted.mkdir();
                continue;
            } else {
                extracted = new File(outputResourceFile, "/" + entry.getFileName());
                if (!extracted.getParentFile().exists()) {
                    extracted.getParentFile().mkdirs();
                }
            }
            extract(zis.getNativeInputStream(ZipInputStream.class), new FileOutputStream(extracted));
        }
        if(isCorrupt){
            log.severe("Input file is corrupted or may not be in ZIP format");
            throw new IOException("Input file is corrupted or may not be in ZIP format");
        }
        log.info("<<< inflate");

    }

    private static void extract(InputStream in, OutputStream out)
            throws IOException {
        byte data[] = new byte[1024];
        int count = 0;
        while ((count = in.read(data)) != -1) {
            out.write(data, 0, count);
            out.flush();
        }
    }

    @Override
    public ArchiveInputStream getArchiveInputStream() {
        return zis;
    }



    @Override
    public void setArchiveFileName(String archiveFilePath) {
        this.inputArchiveResourceStr = archiveFilePath;

    }

    @Override
    public String getArchiveFileName() {
        return this.inputArchiveResourceStr;
    }

    @Override
    public void setOutputLocation(String outputDir) {
      this.outputResourceStr = outputDir;

    }

    @Override
    public String getOutputLocation() {
       return this.outputResourceStr;
    }

    @Override
    public void inflate(String achiveFilePath) throws IOException {
       inflate(achiveFilePath, null);

    }




}
