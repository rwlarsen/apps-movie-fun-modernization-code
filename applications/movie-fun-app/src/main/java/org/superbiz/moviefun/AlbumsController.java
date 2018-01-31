package org.superbiz.moviefun;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.albumsapi.BlobInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static java.lang.String.format;

@Controller
@RequestMapping("/albums")
public class AlbumsController {

    private final AlbumsClient albumsClient;

    public AlbumsController(AlbumsClient albumsClient) {
        this.albumsClient = albumsClient;
    }

    @GetMapping
    public String index(Map<String, Object> model) {
        model.put("albums", albumsClient.getAlbums());
        return "albums";
    }

    @GetMapping("/{albumId}")
    public String details(@PathVariable long albumId, Map<String, Object> model) {
        model.put("album", albumsClient.find(albumId));
        return "albumDetails";
    }

    @PostMapping("/{albumId}/cover")
    public String uploadCover(@PathVariable Long albumId, @RequestParam("file") MultipartFile uploadedFile) {

        albumsClient.uploadCover(albumId, uploadedFile);

        return format("redirect:/albums/%d", albumId);
    }

    @GetMapping("/{albumId}/cover")
    public HttpEntity<byte[]> getCover(@PathVariable long albumId) throws IOException, URISyntaxException {
        BlobInfo blobInfo = albumsClient.getCover(albumId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(blobInfo.contentType));
        headers.setContentLength(blobInfo.bytes.length);

        return new HttpEntity<>(blobInfo.bytes, headers);
    }
}
