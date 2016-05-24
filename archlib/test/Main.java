package archlib.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import archlib.ArchiveDeflater;
import archlib.ArchiveDeflaterFactory;
import archlib.ArchiveInflater;
import archlib.ArchiveInflaterFactory;
import archlib.SupportedFileTypes;

/**
 *
 * Basic tests for inbuilt Inflaters and Deflaters
 * @author Vaman Kulkarni
 */
public class Main extends TestCase {

    private static final String FILE_PATH = "d:/out_dir/hello.txt";

    private static final String DIR_PATH = "d:/out_dir/test_folder";

    private static final String BASE_TEST_DIR_PATH = "d:/out_dir/test_output";


    @Test
    public void testTarDeflater() throws IOException {
        testDeflater(SupportedFileTypes.TAR.strFileType);
    }

    @Test
    public void testZipDeflater() throws IOException {
        testDeflater(SupportedFileTypes.ZIP.strFileType);
    }

    @Test
    public void testTarInflater() throws IOException {
        testInflater("d:/out_dir/hello.tar", "d:/out_dir/test_output/TAR/hello.tar", SupportedFileTypes.TAR.strFileType);
    }

    private void testDeflater(String fileType) throws IOException {
        ArchiveDeflater deflater = ArchiveDeflaterFactory.getArchiveDeflater(fileType);
        deflater.deflate(FILE_PATH);
        File output = new File(deflater.getDeflatedFileName());
        String fileStr = fileType.toUpperCase();
        Assert.assertTrue(fileStr + " file was not generated at default location ", output.exists());

        // deflate a file at specified location
        deflater.deflate(FILE_PATH, BASE_TEST_DIR_PATH + "/" + fileStr);

        // Check if the file has generated.
        output = new File(deflater.getDeflatedFileName());
        Assert.assertTrue(fileStr + " file was not generated at specified location ", output.exists());

        /* Test deflate for folder */
        deflater.deflate(DIR_PATH);
        output = new File(deflater.getDeflatedFileName());
        Assert.assertTrue(fileStr + " file was not generated from a folder at default location ", output.exists());

        /* Test deflate for folder at specified location */
        deflater.deflate(DIR_PATH, BASE_TEST_DIR_PATH + "/" + fileStr + "_ws/");
        output = new File(deflater.getDeflatedFileName());
        Assert.assertTrue(fileStr + " file was not generated from a folder at default location ", output.exists());
    }

    private void testInflater(String fpath, String dirPath, String fileType) throws IOException {

        ArchiveInflater inflater = ArchiveInflaterFactory.getArchiveInflater(fileType);
        inflater.inflate(fpath);
        File output = new File(inflater.getInflatedResource());
        String fileStr = fileType.toUpperCase();
        Assert.assertTrue(fileStr + " file was not generated at default location ", output.exists());

        // inflate a file at specified location
        inflater.inflate(fpath, BASE_TEST_DIR_PATH);

        // Check if the file has generated.
        output = new File(inflater.getInflatedResource());
        Assert.assertTrue(fileStr + " file was not generated at specified location ", output.exists());

        /* Test inflate for folder */
        inflater.inflate(dirPath);
        output = new File(inflater.getInflatedResource());
        Assert.assertTrue(fileStr + " file was not generated from a folder at default location ", output.exists());

        /* Test inflate for folder at specified location */
        inflater.inflate(dirPath, BASE_TEST_DIR_PATH + "/" + fileStr + "inf_ws/");
        output = new File(inflater.getInflatedResource());
        Assert.assertTrue(fileStr + " file was not generated from a folder at default location ", output.exists());
    }

}
