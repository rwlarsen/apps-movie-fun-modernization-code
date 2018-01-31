package org.superbiz.moviefun;

import org.apache.tika.io.IOUtils;
import org.superbiz.moviefun.blobstore.Blob;

import java.io.IOException;

public class BlobInfo {

    public final String name;
    public final byte[] bytes;
    public final String contentType;

    public BlobInfo(String name, byte[] bytes, String contentType) {
        this.name = name;
        this.bytes = bytes;
        this.contentType = contentType;
    }

    public BlobInfo(Blob blob) throws IOException {
        this.name = blob.name;
        this.contentType = blob.contentType;
        this.bytes = IOUtils.toByteArray(blob.inputStream);
    }
}
