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
package archlib.gzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import archlib.ArchiveDeflater;
import archlib.ArchiveOutputStream;
import archlib.util.CustomLogger;

/**
*
* @author Vaman Kulkarni
*/
public class GZIPDeflater implements ArchiveDeflater {

    private static final int DATA_BUFFER_SIZE = 1024;

    private Logger log = CustomLogger.getLogger(GZIPDeflater.class);

    private GZIPFileOutputStream gzis = null;

    private File srcFile;

    private File archFile;


    private void initDeflater(String inDir, String ourArchFile) throws IOException {
        File out= new File(inDir);
        if (!out.exists()) {
            throw new FileNotFoundException(inDir + " is not a valid path");
        }
        if(out.isDirectory()){
            String errMsg = "Cannot GZIP a directory. Please add the directory to an archive (TAR or ZIP) before you GZIP it.";
            log.severe(errMsg);
            throw new IllegalArgumentException(errMsg);
        }
        this.srcFile= out;

        File arch  = new File(ourArchFile);
        if (arch.exists()) {
            log.severe((arch.getName() + " already exists. It will be overwritten"));
            throw new IllegalArgumentException(ourArchFile + " already exists.");
        }
        this.archFile = arch;
        this.gzis = new GZIPFileOutputStream(new BufferedOutputStream(new FileOutputStream(arch)));
    }
    public void deflate(String srcPath) throws IOException {
        String outFileName = srcPath + ".gz";
        deflate(srcPath, outFileName);
    }
    @Override
    public void deflate(String srcPath, String destAbsFilePath) throws IOException {
        log.info("************** Started Deflate() ******************");
        initDeflater(srcPath, destAbsFilePath);

        InputStream fis = new BufferedInputStream(new FileInputStream(srcFile));
        int readCount = 0;
        byte buffer[] = new byte[DATA_BUFFER_SIZE];
        log.info("Compressing data....");
        while((readCount = fis.read(buffer)) != -1){
            gzis.write(buffer,0,readCount);
        }
        log.info("Finished compressing data....");
        gzis.close();
        log.info("************** Finished deflate() ******************");

    }

    @Override
    public String getDeflatedFileName() {
       return this.archFile.getAbsolutePath();
    }
    @Override
    public ArchiveOutputStream getArchiveOutputStream() {
        return gzis;
    }

}
