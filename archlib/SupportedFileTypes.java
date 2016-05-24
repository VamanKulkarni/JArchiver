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

/**
 * These are the currently supported compressed file formats.
 * <ol>
 * <li>TAR</li>
 * <li>ZIP</li>
 * <li>GZIP</li>
 * </ol>
 *
 * @author Vaman Kulkarni
 *
 */
public enum SupportedFileTypes {
    TAR("tar"),
    ZIP("zip"),
    GZIP("gzip");

    public String strFileType;

    SupportedFileTypes(String extn) {
        this.strFileType = extn;
    }
}
