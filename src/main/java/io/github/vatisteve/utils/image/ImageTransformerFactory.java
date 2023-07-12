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

    /**
     * @param transformer   The type of transformer to use
     * @param inputStream   The image input stream data
     * @param mimeType      The image extension
     * @return              Instance of {@link Transformer} with specific transformer type
     * @throws MimeTypeNotSupportedException    the extension does not supported
     * @throws IOException                      exception throw when creating new ImageTransformer implementation
     */
    public static ImageTransformer buildImageTransformer(Transformer transformer, InputStream inputStream,
            String mimeType) throws MimeTypeNotSupportedException, IOException {
        if (transformer == null || transformer == Transformer.MARVIN) {
            return new MarvinFrameworkImageTransformer(inputStream, mimeType);
        } else {
            return null; // ... new instance THUMBNAILATOR
        }
    }

    /**
     * @param inputStream   The image input stream data
     * @param mimeType      The image extension
     * @return              The default instance of {@link Transformer}
     * @throws MimeTypeNotSupportedException    the extension does not supported
     * @throws IOException                      exception throw when creating new ImageTransformer implementation
     */
    public static ImageTransformer buildImageTransformer(InputStream inputStream, String mimeType)
            throws MimeTypeNotSupportedException, IOException {
        // Use MARVIN framework by default
        return buildImageTransformer(Transformer.MARVIN, inputStream, mimeType);
    }

    public enum Transformer {
        MARVIN, THUMBNAILATOR
    }
}
