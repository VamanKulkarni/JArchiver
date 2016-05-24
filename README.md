# JArchiver



JArchiver is a simple and extensible Java library for working with archive and compressed archive files. It provides easy and consistent interface to work with any kind of supported format.
Currently this library supports TAR, ZIP and GZIP formats, but support for other formats can be incorporated easily in the framework.

JArchiver is designed to accomplish task in a very limited lines of code and encapsulates all the complexities under the hood. Due to which the client programs( the programs that use this library) are kept abstracted from the underlying implementation which provides the flexibility to enhance/update underlying code without affecting client programs. The core of the library are two interfaces ArchiveInflater and ArchiveDeflater Refer below examples to understand the usage of the two classes.


##Library Class Diagrams

You may want to extend this library by creating your own Inflaters and Deflaters say for e.g. Jar or BZip2 etc. Referring below figures will provide you an idea of how to do this.

* `ArchiveInflater` Any new inflater which can support reading/writing data in the form of Entries (e.g. ZipEntry, TarEntry) should extend AbstractArchiveInflater. Inflaters that do not support Entry based read/write should implement ArchiveInflater (e.g. GZIPInflater). Of course it is your input/output stream that will decide support for Entries.

http://jarchiver.googlecode.com/svn/wiki/inflater.JPG

* `ArchiveDeflater` Same guidelines are applied to the new Deflaters as that of Inflaters stated above. http://jarchiver.googlecode.com/svn/wiki/deflater.JPG

* `ArchiveInputStream` As you can see from the figure below, there is an extra layer of wrapper which wraps the native input/output streams. i.e. ZipFileInputStream wraps java.util.zip.ZipInputStream or TarFileInputStream wraps archlib.tarlib.TarInputStream etc. http://jarchiver.googlecode.com/svn/wiki/inputstream.JPG

* `ArchiveOutputStream` ArchiveOutputStream structure resembles with the ArchiveInputStream. It too has wrapper layer and wraps corresponding OutpuStreams. http://jarchiver.googlecode.com/svn/wiki/outputstream.JPG
* 


###Examples

* Compressing to a zip file Step1: Get the deflater for desired file format. ` ArchiveDeflater compressor = ArchiveDeflaterFactory.getArchiveDeflater(SupportedFileTypes.ZIP);`


* Step 2: Invoke `deflate()` method on the `ArchiveDeflater deflater.deflate(inputFile,outputLocation);` outputLocation is optional. If not supplied then the inputFile will be archived in the same directory as that of inputFile. The output file name will be same as that of input file but with the .zip extension

   * Creating a TAR archive `ArchiveDeflater compressor = ArchiveDeflaterFactory.getArchiveDeflater(SupportedFileTypes.TAR);` `compressor.deflate(inputFile,outputLocation)`

   * Extracting files from a ZIP file Step1: Get the inflater for desired file format. ` ArchiveInflater decompressor = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.ZIP);`



* Step 2: Invoke `inflate()` method on the `ArchiveInflater decompressor.inflate(inputArchive,outputLocation);`

Again `outputLocation` is optional. If not supplied then the inputArchive will be extracted in the same directory as that of inputArchive.

  * Extracting from a TAR archive `ArchiveInflater compressor = ArchiveInflaterFactory.getArchiveInflater(SupportedFileTypes.TAR); decompressor.inflate(inputArchive,outputLocation)`
For more examples refer JUnit test `archlib.test.Main`


This project was earlier hosted on Google code and was recently migrated to githib
