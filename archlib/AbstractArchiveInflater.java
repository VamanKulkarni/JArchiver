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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import archlib.tarlib.TarInputStream;
import archlib.util.CustomLogger;

/**
 * Provides ground work for the <b>Entry</b> based inflaters, i.e. Inflaters that support Entry by Entry decompression. e.g. TarEntry ZipEntry.
 * @author Vaman Kulkarni
 */
public abstract class AbstractArchiveInflater implements ArchiveInflater {

    private Logger log = CustomLogger.getLogger(AbstractArchiveInflater.class);

    protected String inputArchiveResourceStr = null;

    protected String outputResourceStr = null;

    /**
     * Archive to inflate
     */
    protected File inputArchiveResourceFile = null;

    /**
     * Inflated output File
     */
    protected File outputResourceFile = null;

    protected void initInflater(String file, String outResource) throws IOException {
        File arch = new File(file);
        if (!arch.exists()) {
            throw new FileNotFoundException(file + " is not a valid path");
        }
        if(arch.length() < 1){
            throw new IOException("Input file is empty. Please provide a non-empty valid file");
        }
        this.inputArchiveResourceFile = arch;
        if (outResource == null) {
            outResource = this.inputArchiveResourceFile.getParentFile().getAbsolutePath();
        }
        File outFile = new File(outResource);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        this.outputResourceFile = outFile;
        this.outputResourceStr = this.outputResourceFile.getAbsolutePath();

    }

    protected void doSilentInflate(AbstractArchiveInputStream inStream) throws IOException {
        if (!outputResourceFile.exists()) {
            log.info(outputResourceFile.getName()
                    + " does not exists hence creating a new one");
            outputResourceFile.mkdir();
        }

        boolean isCorrupt = true;
        ArchiveEntry entry = null;
        log.info("Start parsing entries from the compressed file");
        while ((entry = inStream.getNextEntry()) != null) {
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

            extract(inStream.getNativeInputStream(TarInputStream.class), new BufferedOutputStream(new FileOutputStream(extracted)));
        }
        if(isCorrupt){
            log.severe("Input file is corrupted or may not be in TAR format");
        }
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
    public String getInflatedResource() {
        return outputResourceStr;
    }

    /**
     * Returns the underlying <code>ArchiveInputStream</code> associated with {@code  this} inflater instance.
     *
     * @return <code>ArchiveInputStream</code>
     */
    public abstract ArchiveInputStream getArchiveInputStream();
}
