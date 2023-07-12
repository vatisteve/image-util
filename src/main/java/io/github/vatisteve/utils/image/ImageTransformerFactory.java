package io.github.vatisteve.utils.image;

import java.io.IOException;
import java.io.InputStream;

import io.github.vatisteve.utils.image.impl.MarvinFrameworkImageTransformer;

/**
 * @author Steve
 * @since Jun 11, 2023
 *
 */
public final class ImageTransformerFactory {

    private ImageTransformerFactory() {}

    public static ImageTransformer buildImageTransformer(Transformer transformer, InputStream inputStream,
            String mimeType) throws MimeTypeNotSupportedException, IOException {
        if (transformer == null || transformer == Transformer.MARVIN) {
            return new MarvinFrameworkImageTransformer(inputStream, mimeType);
        } else {
            return null; // ... new instance THUMBNAILATOR
        }
    }

    public static ImageTransformer buildImageTransformer(InputStream inputStream, String mimeType)
            throws MimeTypeNotSupportedException, IOException {
        // Use MARVIN framework by default
        return buildImageTransformer(Transformer.MARVIN, inputStream, mimeType);
    }

    public enum Transformer {
        MARVIN, THUMBNAILATOR
    }
}
