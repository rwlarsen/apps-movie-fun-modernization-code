package org.superbiz.moviefun.albums;

import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.BlobInfo;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AlbumsBean albumsBean;
    private final BlobStore blobStore;
    private final Tika tika = new Tika();

    public AlbumsController(AlbumsBean albumsBean, BlobStore blobStore) {
        this.albumsBean = albumsBean;
        this.blobStore = blobStore;
    }

    @GetMapping
    public List<Album> findAll() {
        return albumsBean.getAlbums();
    }

    @GetMapping("/{id}")
    public Album find(@PathVariable long id) {
        return albumsBean.find(id);
    }

    @PostMapping
    public void create(@RequestBody Album album){
        albumsBean.addAlbum(album);
    }


    @PostMapping("/{albumId}/cover")
    public void uploadCover(@PathVariable Long albumId, @RequestBody byte[] bytes) {
        logger.debug("Uploading cover for album with id {}", albumId);


        try {
            tryToUploadCover(albumId, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{albumId}/cover")
    public BlobInfo getCover(@PathVariable long albumId) throws IOException, URISyntaxException {
        Optional<Blob> maybeCoverBlob = blobStore.get(getCoverBlobName(albumId));
        Blob coverBlob = maybeCoverBlob.orElseGet(this::buildDefaultCoverBlob);

        return new BlobInfo(coverBlob);
    }


    private void tryToUploadCover(Long albumId, byte[] bytes) throws IOException {
        Blob coverBlob = new Blob(
                getCoverBlobName(albumId),
                new ByteArrayInputStream(bytes),
                tika.detect(bytes)
        );

        blobStore.put(coverBlob);
    }

    private Blob buildDefaultCoverBlob() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("default-cover.jpg");

        return new Blob("default-cover", input, MediaType.IMAGE_JPEG_VALUE);
    }

    private String getCoverBlobName(@PathVariable long albumId) {
        return format("covers/%d", albumId);
    }
}
