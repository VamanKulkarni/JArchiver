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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import archlib.AbstractArchiveInflater;
import archlib.ArchiveInputStream;
import archlib.util.CustomLogger;

/**
 * Inflater for TAR archives
 * @author Vaman Kulkarni
 */
public class TarInflater extends AbstractArchiveInflater {
    private static Logger log = CustomLogger.getLogger(TarInflater.class);

    private TarFileInputStream inStream = null;

    public TarInflater(String filePath) throws IOException {
        initInflater(filePath, null);
    }

    public TarInflater() {
    }

    public TarInflater(File file) throws IOException {
        this(file.getAbsolutePath());
    }

    @Override
    public void inflate() throws IOException {
        inflate(this.inputArchiveResourceStr, this.outputResourceStr);
    }

    @Override
    public void inflate(String archiveFilePath, String outputDir) throws IOException {
        log.info("************** Started inflate() ******************");

        initInflater(archiveFilePath, outputDir);
        this.inStream = new TarFileInputStream(new BufferedInputStream(new FileInputStream(this.inputArchiveResourceFile)));

        doSilentInflate(inStream);

        log.info("************** Finished inflate() ******************");

    }

    @Override
    public ArchiveInputStream getArchiveInputStream() {
        return inStream;
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

//    @Override
//    public String getInflatedResource() {
//        return this.outputResourceFile.getAbsolutePath();
//    }

}
