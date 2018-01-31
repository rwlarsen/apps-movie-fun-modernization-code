package org.superbiz.moviefun.albumsapi;

public class BlobInfo {

    public String name;
    public byte[] bytes;
    public String contentType;

    public BlobInfo(){}

    public BlobInfo(String name, byte[] bytes, String contentType) {
        this.name = name;
        this.bytes = bytes;
        this.contentType = contentType;
    }


}
