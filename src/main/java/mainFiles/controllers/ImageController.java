package mainFiles.controllers;

import mainFiles.Data.ImageData;
import mainFiles.objects.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageData imageData;

    /*
     * Returns the raw image bytes for a given image ID.
     * @param id The ID of the image
     * @return The image as bytes with the correct Content-Type
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageData.findById(id).orElse(null);
        if (image == null || image.getImageData() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String contentType = image.getImageType() != null ? image.getImageType() : "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(image.getImageData());
    }

}
