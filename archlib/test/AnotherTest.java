package archlib.test;

import java.io.IOException;

import archlib.ArchiveInflater;
import archlib.ArchiveInflaterFactory;
import archlib.SupportedFileTypes;

public class AnotherTest {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String input = "d:/out_dir/hello.tar.gz";
        String output = "d:/out_dir/extracted";

//        ArchiveDeflater def = ArchiveDeflaterFactory.getArchiveDeflater(SupportedFileTypes.TAR);
//        def.deflate(input);



        ArchiveInflater in = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.GZIP);
        in.inflate(input);


        input = "d:/out_dir/huge.tar";
        in = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.TAR);
        in.inflate(input, output+"/untar");

        input = "d:/out_dir/huge.zip";
        in = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.GZIP);
        in.inflate(input, output+"/ungziped");

        input = "d:/out_dir/huge.tar";
        in = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.GZIP);
        in.inflate(input, output+"/ungztared");
    }

}
