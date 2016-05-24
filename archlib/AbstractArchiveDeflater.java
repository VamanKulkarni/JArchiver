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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import archlib.util.CustomLogger;

/**
 * Provides some foundation for <b>Entry</b> based deflaters, i.e. deflaters which support accessing each Entry. e.g. TarEntry, ZipEntry etc.
 * Also has the implementation of some of the methods from {@code ArchiveDeflater}
 *
 * @author Vaman Kulkarni
 */
public abstract class AbstractArchiveDeflater implements ArchiveDeflater {

    private Logger log = CustomLogger.getLogger(AbstractArchiveDeflater.class);

    /**
     * Archive file to deflate
     */
    protected File inputResourceFile = null;

    /**
     * Resultant archive after deflate.
     */
    protected File resultResourceFile = null;


    protected void initDeflater(String srcDir, String outResource, String fileType) throws IOException {
        File out = new File(srcDir);
        if (!out.exists()) {
            throw new FileNotFoundException(srcDir + " is not a valid path");
        }
        this.inputResourceFile = out;
        if (outResource == null) {
               outResource = getResultResourcePath(inputResourceFile) + fileType;
        }else{
            outResource = outResource + "/"+inputResourceFile.getName().split("\\.")[0]+"."+fileType;
        }
        File arch = new File(outResource);
        if (arch.exists()) {
            log.info(arch.getName() + " already exists. It will be overwritten");
        }else{
            arch.getParentFile().mkdirs();
            arch.createNewFile();
        }
        this.resultResourceFile = arch;
    }

    protected String getResultResourcePath(File inRes){
        if(inRes.isDirectory()){
            return inRes.getAbsolutePath().concat(".");
        }else{
            String arr[] = inRes.getName().split("\\.");
            if(arr.length > 1){
                //Files with extension
                return inRes.getParentFile().getAbsolutePath() + "/" + arr[0] + ".";
            }else{
                //Files without extension
                return inRes.getAbsolutePath()+".";
            }
        }
    }

    /**
     * Returns the {@code ArchiveOutputStream  }associated with {@code this } Deflater.
     * @return ArchiveOutputStream
     */
    public abstract ArchiveOutputStream getArchiveOutputStream();



    @Override
    public String getDeflatedFileName() {
        return resultResourceFile.getAbsolutePath();
    }

}
