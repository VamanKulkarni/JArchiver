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

import java.io.InputStream;

/**
 * Provides an abstraction for the underlying file specific inputstream.
 * @author Vaman Kulkarni
 *
 */
public interface ArchiveInputStream {

    /**
     * Returns the underlying file type specific {@code InputStream}.
     * @param <X>
     * @param streamClass The inputstream class that you are expecting
     * @return
     */
	public  <X extends InputStream> X  getNativeInputStream(Class<X> streamClass);

}
