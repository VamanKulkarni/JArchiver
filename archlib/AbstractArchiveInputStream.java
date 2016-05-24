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

import java.io.IOException;

/**
 *
 * All the Entry based deflater specific input streams should inherit from this class.
 *
 * @author Vaman Kulkarni
 */
public abstract class AbstractArchiveInputStream implements ArchiveInputStream {

    /**
     * Returns you the next valid  {@code  ArchiveEntry}  from the compressed file. Returns null once the last entry is read from file.
     * @return  GenericEntry from the compressed file or null after reaching last entry.
     * @throws Exception
     */
    public abstract ArchiveEntry getNextEntry() throws IOException;

}
