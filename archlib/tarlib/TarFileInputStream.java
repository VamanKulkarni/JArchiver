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
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import archlib.AbstractArchiveInputStream;
import archlib.ArchiveEntry;
import archlib.util.CustomLogger;

/**
 *
 * @author Vaman Kulkarni
 */
public class TarFileInputStream extends AbstractArchiveInputStream {

    private Logger log = CustomLogger.getLogger(TarFileInputStream.class);

    private TarInputStream tarIn = null;


    public TarFileInputStream(InputStream in) throws FileNotFoundException {
        tarIn = new TarInputStream(in);
    }

    @Override
    public <X extends InputStream> X getNativeInputStream(Class<X> streamClass) {
        return streamClass.cast(tarIn);
    }

    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return tarIn.getNextEntry();
    }



}
