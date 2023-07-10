package io.github.vatisteve.utils.image;

public class MimeTypeNotSupportedException extends Exception {

    private static final long serialVersionUID = -5710039736629119895L;

    public MimeTypeNotSupportedException(String mimeType) {
        super(String.format("This MIME types does not supported: %s", mimeType));
    }
}
