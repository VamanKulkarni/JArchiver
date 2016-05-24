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
 * Base class for all the output streams.
 *
 * @author Vaman Kulkarni
 *
 */
public interface ArchiveOutputStream {


	/**
	 * Expected to return the underlying {@code OutputStream} that is wrapped by
     * the {@link ArchiveOutputStream}.
     * This is useful if you explicitly want to use additional capabilities (if any) provided by the
     * archive specific output stream.
	 * @param <X>
	 * @param streamClass It is the class that you expect this method to return
	 * @return Underlying {@code OutputStream}
	 */
	public <X extends InputStream> X getNativeOutputStream(Class<X> streamClass);


}
