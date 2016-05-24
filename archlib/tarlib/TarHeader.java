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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import archlib.util.CustomLogger;

/**
 * Extracts and encapsulates the header information of a TAR archive.
 * @author Vaman Kulkarni
 */
public class TarHeader {
    private Logger log = CustomLogger.getLogger(TarHeader.class);


    private static final int FILE_SIZE_LEN = 12;

    private static final int FILE_MODE_LEN = 8;

    private static final int FILE_NAME_LEN = 100;

    private static final int OWNER_NUMERIC_USR_ID_LEN = 8;

    private static final int OWNER_NUMERIC_GRP_ID_LEN = 8;

    private static final int OWNER_USERNAME_LEN = 32;

    private static final int OWNER_GROUP_LEN = 32;

    private static final int LAST_MODIFIED_LEN = 12;

    private static final int USTAR_LEN = 6;

    private static final int USTAR_VERSION_LEN = 2;

    private static final int CHKSUM_LEN = 8;

    private static final int LINK_INDICATOR_LEN = 1;

    private static final int LINKED_FILE_NAME_LEN = 100;

    private static final int DEVICE_MAJOR_LEN = 8;

    private static final int DEVICE_MINOR_LEN = 8;

    private static final int FILE_NAME_PREFIX_LEN = 155;

    private static final int LAST_MODIFIED_OFFSET = 136;

    private static final int FILE_SIZE_OFFSET = 124;

    private static final int FILE_MODE_OFFSET = 100;

    private static final int LINK_INDICATOR_OFFSET = 156;

    private static final int OWNER_NUMERIC_USR_ID_OFFSET = 108;

    private static final int OWNER_NUMERIC_GRP_ID_OFFSET = 116;

    private static final int OWNER_USERNAME_OFFSET = 265;

    private static final int OWNER_GROUP_OFFSET = 297;

    private static final int DEVICE_MAJOR_OFFSET = 329;

    private static final int DEVICE_MINOR_OFFSET = 337;

    private static final int FILE_NAME_PREFIX_OFFSET = 345;

    private static final int FILE_NAME_OFFSET = 0;

    private static final int USTAR_OFFSET = 257;

    private static final int CHKSUM_OFFSET = 148;

    private static final int USTAR_VERSION_OFFSET = 263;

    private static final String USTAR = "ustar";

    private static final String USTAR_VERSION = "00";

    private static final int OCTAL = 8;

    private static final int BYTE_MASK = 255;

    private byte[] header;

    private String fileName;

    private long fileMode;

    private long ownerNumericUserId;

    private long ownerNumericGrpId;

    private long lastModifiedTimeStamp;

    private long checksum = 0;

    private byte linkIndicator;

    private String linkedFileName = null;

    private long fileSize = 0;

    private String userName = null;

    private String grpName = null;

    private long deviceMajor = 0;

    private long deviceMinor = 0;

    private String fileNamePrefix = null;

    /**
     * List of supported indicators
     *
     * @author Vaman Kulkarni
     */
    private enum LinkIndicator {
        NORMAL_FILE(0),
        DIRECTORY(5);

        byte sBit;

        private LinkIndicator(int _sBit) {
            this.sBit = (byte) _sBit;
        }
    }

    /**
     * Initializes a TarHeader with the supplied buffer. Then it extracts header information and populates the header
     * attributes.
     *
     * @param buff
     *            Header read from an archive.
     * @throws IOException
     */
    public TarHeader(byte buff[]) throws IOException {
        this.header = buff;
        initlializeHeaderFields();
    }

    /**
     * Initializes a TarHeader by reading the supplied file attributes and then populates the header attributes.
     *
     * @param file
     * @param ppath
     */
    public TarHeader(File file, String ppath) {
        this.header = new byte[TarLibConstants.TAR_HEADER_SIZE];
        initlializeHeaderFields(file, ppath);
        writeToBuffer();
    }

    private void initlializeHeaderFields() throws IOException {
        this.setFileName(stringParser(FILE_NAME_OFFSET, FILE_NAME_LEN));
        this.setFileMode(numberParser(FILE_MODE_OFFSET, FILE_MODE_LEN));

        this.setOwnerNumericGrpId(numberParser(OWNER_NUMERIC_GRP_ID_OFFSET, OWNER_NUMERIC_GRP_ID_LEN));
        this.setOwnerNumericUserId(numberParser(OWNER_NUMERIC_USR_ID_OFFSET, OWNER_NUMERIC_USR_ID_LEN));

        this.setFileSize(numberParser(FILE_SIZE_OFFSET, FILE_SIZE_LEN));

        long li = numberParser(LINK_INDICATOR_OFFSET, LINK_INDICATOR_LEN);
        if (li == 0) {
            this.setLinkIndicator(LinkIndicator.NORMAL_FILE);
        } else if (li == 5) {
            this.setLinkIndicator(LinkIndicator.DIRECTORY);
        } else {
            throw new UnsupportedOperationException(li + " is not supported as LinkIndicator currently");
        }


        this.setLastModifiedTimeStamp(numberParser(LAST_MODIFIED_OFFSET, LAST_MODIFIED_LEN));

        this.setUserName(stringParser(OWNER_USERNAME_OFFSET, OWNER_USERNAME_LEN));

        this.setGrpName(stringParser(OWNER_GROUP_OFFSET, OWNER_GROUP_LEN));

        this.setDeviceMajor(numberParser(DEVICE_MAJOR_OFFSET, DEVICE_MAJOR_LEN));

        this.setDeviceMinor(numberParser(DEVICE_MINOR_OFFSET, DEVICE_MINOR_LEN));

        this.setFileNamePrefix(stringParser(FILE_NAME_PREFIX_OFFSET, FILE_NAME_PREFIX_LEN));
    }

    private void initlializeHeaderFields(File target, String ppath) {
        this.setUserName(System.getProperty("user.name"));
        this.setOwnerNumericGrpId(0);
        this.setOwnerNumericUserId(0);

        if ("/".equals(ppath)) {
            this.setFileName(target.getName() + ppath);
        } else if (!"".equals(ppath)) {
            this.setFileName(ppath + target.getName());
        } else {
            this.setFileName(target.getName());
        }

        this.setFileSize(target.length());

        if (target.isDirectory()) {
            this.setLinkIndicator(LinkIndicator.DIRECTORY);
        } else {
            this.setLinkIndicator(LinkIndicator.NORMAL_FILE);
            this.setFileMode(0100644);
        }
        this.setLastModifiedTimeStamp(target.lastModified() / 1000);

    }

    private void writeToBuffer() {
        int offset = 0;
        try {

            byte octalBytes[];
            octalBytes = this.getFileName().getBytes("US-ASCII");
            writeStringData(octalBytes, offset, FILE_NAME_LEN);
            offset += FILE_NAME_LEN;

            octalBytes = getOctalBytes(getFileMode());
            writeLongData(octalBytes, offset, FILE_MODE_LEN);
            offset += FILE_MODE_LEN;

            octalBytes = getOctalBytes(getOwnerNumericUserId());
            writeLongData(octalBytes, offset, OWNER_NUMERIC_GRP_ID_LEN);
            offset += OWNER_NUMERIC_GRP_ID_LEN;

            octalBytes = getOctalBytes(getOwnerNumericGrpId());
            writeLongData(octalBytes, offset, OWNER_NUMERIC_GRP_ID_LEN);
            offset += OWNER_NUMERIC_GRP_ID_LEN;

            octalBytes = getOctalBytes(getFileSize());
            writeLongData(octalBytes, offset, FILE_SIZE_LEN);
            offset += FILE_SIZE_LEN;

            octalBytes = getOctalBytes(getLastModifiedTimeStamp());
            writeLongData(octalBytes, offset, LAST_MODIFIED_LEN);
            offset += LAST_MODIFIED_LEN;

            octalBytes = "        ".getBytes("US-ASCII");
            writeBufferAsIs(octalBytes, offset, CHKSUM_LEN);
            offset += CHKSUM_LEN;

            octalBytes = getOctalBytes(getLinkIndicator());
            writeBufferAsIs(octalBytes, offset, LINK_INDICATOR_LEN);
            offset += LINK_INDICATOR_LEN;

            offset += LINKED_FILE_NAME_LEN;

            octalBytes = USTAR.getBytes("US-ASCII");
            writeStringData(octalBytes, offset, USTAR_LEN);
            offset += USTAR_LEN;

            octalBytes = USTAR_VERSION.getBytes("US-ASCII");
            writeStringData(octalBytes, offset, USTAR_VERSION_LEN);
            offset += USTAR_VERSION_LEN;

            octalBytes = getUserName().getBytes("US-ASCII");
            writeStringData(octalBytes, offset, OWNER_USERNAME_LEN);
            offset += OWNER_USERNAME_LEN;

            if (getGrpName() != null) {
                octalBytes = getGrpName().getBytes("US-ASCII");
                writeStringData(octalBytes, offset, OWNER_GROUP_LEN);

            }
            offset += OWNER_GROUP_LEN;

            octalBytes = getOctalBytes(getDeviceMajor());
            writeLongData(octalBytes, offset, DEVICE_MAJOR_LEN);
            offset += DEVICE_MAJOR_LEN;

            octalBytes = getOctalBytes(getDeviceMinor());
            writeLongData(octalBytes, offset, DEVICE_MINOR_LEN);
            offset += DEVICE_MINOR_LEN;

            if (getFileNamePrefix() != null) {
                octalBytes = getFileNamePrefix().getBytes("US-ASCII");
                writeStringData(octalBytes, offset, FILE_NAME_PREFIX_LEN);
            }
            offset += FILE_NAME_PREFIX_LEN;

            long chksum = computeCheckSum(this.header);
            writeLongData(getOctalBytes(chksum), CHKSUM_OFFSET, CHKSUM_LEN);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Compute the checksum of a tar entry header.
     *
     * @param buf
     *            The tar entry's header buffer.
     * @return The computed checksum.
     */
    public static long computeCheckSum(final byte[] buf) {
        long sum = 0;

        for (int i = 0; i < buf.length; ++i) {
            sum += BYTE_MASK & buf[i];
        }

        return sum;
    }

    private byte[] getOctalBytes(long octalValue) {
        String octStr = Long.toOctalString(octalValue);
        try {
            return octStr.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            log.severe("Encoding not supported.");
            e.printStackTrace();
        }
        return null;
    }

    private void writeLongData(byte[] buffer, int offset, int length) {
        int idx = length - 1;
        int wrtPtr = idx == 0 ? 0 : (idx - buffer.length);
        int i = 0;
        if (buffer.length < idx) {
            for (i = offset; i < offset + wrtPtr; i++) {
                header[i] = (byte) '0';
            }
        } else {
            i = offset;
        }
        int cnt = 0;
        for (; i < offset + idx; i++) {
            header[i] = buffer[cnt++];
        }
        header[i] = (byte) 0;
    }

    private void writeStringData(byte[] buffer, int offset, int length) {
        int i = 0;
        int cnt = 0;
        for (i = offset; i < offset + buffer.length; i++) {
            header[i] = buffer[cnt++];
        }
        for (; i < offset + length; i++) {
            header[i] = (byte) 0;
        }

    }

    private void writeBufferAsIs(byte[] buffer, int offset, int length) {
        int cnt = 0;
        for (int i = offset; i < offset + buffer.length; i++) {
            header[i] = buffer[cnt++];
        }
    }

    /**
     * Returns you the buffer associated with <code>this</code> header.
     *
     * @return
     */
    public byte[] getBuffer() {
        return this.header;
    }

    public void setBuffer(byte[] _buffer) {
        this.header = _buffer;
    }

    /**
     * Getter method for file name
     *
     * @return File name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter method to set the name of the file.
     *
     * @param fileName
     *            File name to set in the tar file.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the file size in bytes.
     *
     * @return File size in bytes.
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Returns the file type. Currently only two types are supported. 1. Normal File 2. Directory.
     *
     * @return
     */
    public byte getLinkIndicator() {
        return this.linkIndicator;
    }

    private String stringParser(int offset, int tlen) {
        StringBuffer name = new StringBuffer(tlen + 1);
        for (int i = offset; i < tlen; i++) {
            if (header[i] == 0)
                break; // These values are just for padding. Hence ignore them.
            name.append((char) header[i]);
        }
        return name.toString();
    }

    private long numberParser(int offset, int tlen) throws IOException {
        StringBuffer name = new StringBuffer(tlen + 1);
        tlen = offset + tlen;
        for (int i = offset; i < tlen; i++) {
            if (header[i] == 0)
                break; // These values are just for padding. Hence ignore them.
            name.append((char) header[i]);
        }

        if (name.length() > 0)
            try {
                return Integer.parseInt(name.toString().trim(), OCTAL);
            } catch (NumberFormatException ex) {
                throw new IOException("Corrupted input file or file is not in TAR format");
            }
        else
            return 0;

    }

    /**
     * @return
     */
    public long getFileMode() {
        return fileMode;
    }

    /**
     * @param fileMode
     */
    public void setFileMode(long fileMode) {
        this.fileMode = fileMode;
    }

    /**
     * @return
     */
    public long getOwnerNumericUserId() {
        return ownerNumericUserId;
    }

    /**
     * @param ownerNumericUserId
     */
    public void setOwnerNumericUserId(long ownerNumericUserId) {
        this.ownerNumericUserId = ownerNumericUserId;
    }

    /**
     * @return
     */
    public long getOwnerNumericGrpId() {
        return ownerNumericGrpId;
    }

    /**
     * @param ownerNumericGrpId
     */
    public void setOwnerNumericGrpId(long ownerNumericGrpId) {
        this.ownerNumericGrpId = ownerNumericGrpId;
    }

    /**
     * @return
     */
    public long getLastModifiedTimeStamp() {
        return lastModifiedTimeStamp;
    }

    /**
     * @param lastModifiedTimeStamp
     */
    public void setLastModifiedTimeStamp(long lastModifiedTimeStamp) {
        this.lastModifiedTimeStamp = lastModifiedTimeStamp;
    }

    /**
     * @return
     */
    public long getChecksum() {
        return checksum;
    }

    /**
     * @param checksum
     */
    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public String getLinedFileName() {
        return linkedFileName;
    }

    public void setLinedFileName(String linedFileName) {
        this.linkedFileName = linedFileName;
    }

    public void setLinkIndicator(LinkIndicator linkIndicator) {
        this.linkIndicator = linkIndicator.sBit;
    }

    /**
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return
     */
    public String getGrpName() {
        return grpName;
    }

    /**
     * @param grpName
     */
    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    /**
     * @return
     */
    public long getDeviceMajor() {
        return deviceMajor;
    }

    /**
     * @param deviceMajor
     */
    public void setDeviceMajor(long deviceMajor) {
        this.deviceMajor = deviceMajor;
    }

    /**
     * @return
     */
    public long getDeviceMinor() {
        return deviceMinor;
    }

    /**
     * @param deviceMinor
     */
    public void setDeviceMinor(long deviceMinor) {
        this.deviceMinor = deviceMinor;
    }

    /**
     * @return
     */
    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    /**
     * @param fileNamePrefix
     */
    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public static String getUstarVersion() {
        return USTAR_VERSION;
    }
}
