package org.superbiz.moviefun.albumsapi;

import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class AlbumsClient {

    private final RestOperations restOperations;
    private final String endpoint;

    public AlbumsClient(RestOperations restOperations, String endpoint) {
        this.restOperations = restOperations;
        this.endpoint = endpoint;
    }

    public AlbumInfo find(long id) {
        return restOperations.getForObject(endpoint + "/" + id, AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.getForObject(endpoint, List.class);
    }

    public void uploadCover(long albumId, MultipartFile uploadedFile) {
        byte[] bytes;
        if (!uploadedFile.isEmpty()){
            try {
                bytes = uploadedFile.getBytes();
                restOperations.postForEntity(endpoint + "/" + albumId +"/cover", bytes, byte[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public BlobInfo getCover(long albumId) {
        return restOperations.getForObject(endpoint + "/" + albumId + "/cover", BlobInfo.class);
    }

    public void addAlbum(AlbumInfo album) {
        restOperations.postForEntity(endpoint, album, AlbumInfo.class);
    }
}
