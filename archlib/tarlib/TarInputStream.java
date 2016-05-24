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

import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import archlib.util.CustomLogger;

/**
 * Current version of this class provides support for untar operation. Use read buffer at your judgment <BR>
 * @author Vaman Kulkarni
 *
 */
public class TarInputStream extends FilterInputStream {
    private Logger log = CustomLogger.getLogger(TarInputStream.class);

    private int read_bytes = 0;

    private TarEntry currentEntry = null;

    private long currentFileSize = 0;

    public TarInputStream(InputStream str) throws FileNotFoundException {
        super(str);

    }

    /**
     * Returns you the next available entry in the tar file. Returns null if no more entries are available.
     *
     * @return null if no more entries are available.
     * @throws IOException
     */

    public TarEntry getNextEntry() throws IOException {
        log.info("Initializing next entry");
        initNextEntry();

        byte header[] = new byte[TarLibConstants.TAR_HEADER_SIZE];
        log.info("Reading header of size " + TarLibConstants.TAR_HEADER_SIZE + " bytes");
        int _bytes = super.read(header);

        if (_bytes < 1) {
            throw new IOException("Invalid header found");
        }
        // Check if header is not filled with NULL record.
        for (byte b : header) {
            if (b != 0) {
                currentEntry = new TarEntry(header);
                currentFileSize = currentEntry.getFileSize();
                break;
            }
        }
        System.out.println();
        return currentEntry;

    }

    /**
     * Initializes the next entry. Bookkeeping for read counters and padding.
     *
     * You don't want to read this. Read at your own risk
     *
     * @throws IOException
     */
    private void initNextEntry() throws IOException {
        if (currentEntry != null) {
            // calculate block to skip. we don't want to read.

            long padding = getPaddingByteSize(currentFileSize);
            if (padding == 0) {
                readCounter = 0;
                currentEntry = null;
                return;
            }
            log.info("FZ[" + currentFileSize + "], PAD[" + padding + "], RB[" + read_bytes + "]");
            // if previous read attempt was not complete; adjust the counters.
            if (this.read_bytes < currentFileSize) {
                super.skip((currentFileSize - read_bytes));
                this.read_bytes += (currentFileSize - read_bytes);

            }

            skip(padding);
            log.info("Updating counters");
            currentEntry = null;
            readCounter = 0;
            read_bytes += (padding);

        }
    }

    /**
     * Returns the padding required for this entry
     *
     * @param fileSize
     * @return
     */
    private long getPaddingByteSize(long fileSize) {
        long possiblePadding = fileSize % TarLibConstants.TAR_HEADER_SIZE;
        if (possiblePadding > 0) {
            return TarLibConstants.TAR_HEADER_SIZE - possiblePadding;
        }
        return possiblePadding;
    }

    /**
     * @uml.property name="readCounter"
     */
    private int readCounter = 0;

    @Override
    public int read(byte[] buff, int off, int len) throws IOException {
        int howMuch = 0;

        if (null != currentEntry) {
            if (readCounter >= currentFileSize) {
                return -1;
            }
            if (len > currentFileSize) {
                len = (int) currentFileSize;
            }
            if (readCounter < currentFileSize) {
                len = (int) currentFileSize - readCounter;
                if (len > buff.length) {
                    len = buff.length;
                }
            }
        }
        howMuch = super.read(buff, 0, len);
        // if its a first pass then you are reading header and not the data
        // block
        if (null != currentEntry)
            readCounter += howMuch;

        read_bytes += howMuch;
        return howMuch;
    }

    /**
     * Read a byte
     *
     * @see java.io.FilterInputStream#read()
     */
    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException("This signiture is not currently supported");
    }

}
