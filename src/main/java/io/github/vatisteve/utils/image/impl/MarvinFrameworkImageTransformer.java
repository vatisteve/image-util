package io.github.vatisteve.utils.image.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import io.github.vatisteve.utils.image.FrameProperties;
import io.github.vatisteve.utils.image.ImageTransformer;
import io.github.vatisteve.utils.image.MimeTypeNotSupportedException;
import marvin.image.MarvinImage;
import marvinplugins.MarvinPluginCollection;

public class MarvinFrameworkImageTransformer implements ImageTransformer {

    public static final List<String> TYPES_SUPPORTED = Collections.unmodifiableList(
            Arrays.asList("BMP", "GIF", "JPEG", "JPG", "PNG", "PPM")
    );

    private final MarvinImage marvinImage;

    public MarvinFrameworkImageTransformer(InputStream imageInputStream, String mimeType)
        throws MimeTypeNotSupportedException, IOException {
        if (mimeType != null && !TYPES_SUPPORTED.contains(mimeType.toUpperCase())) {
            throw new MimeTypeNotSupportedException(mimeType);
        }
        BufferedImage img = ImageIO.read(imageInputStream);
        this.marvinImage = mimeType == null ? new MarvinImage(img) : new MarvinImage(img, mimeType);
    }

    @Override
    public ByteArrayOutputStream resize(FrameProperties size) throws IOException {
        return resize(size.getWidth(), size.getHeight());
    }

    @Override
    public ByteArrayOutputStream resize(int width, int height) throws IOException {
        return toInputStream(doResize(width, height));
    }

    private MarvinImage doResize(int width, int height) {
        MarvinImage imgOut = new MarvinImage(); // ...
        MarvinPluginCollection.scale(marvinImage, imgOut, width, height);
        return imgOut;
    }

    @Override
    public ByteArrayOutputStream scale(double scale) throws IOException {
        int newWidth = (int) Math.round(scale*marvinImage.getWidth());
        int newHeight = (int) Math.round(scale*marvinImage.getHeight());
        return resize(newWidth, newHeight);
    }

    @Override
    public ByteArrayOutputStream scaleByWidth(FrameProperties size) throws IOException {
        return scaleByWidth(size.getWidth());
    }

    @Override
    public ByteArrayOutputStream scaleByWidth(int width) throws IOException {
        return toInputStream(doScaleByWidth(width));
    }

    private MarvinImage doScaleByWidth(int width) {
        double scale = (double) width/marvinImage.getWidth();
        int newHeight = (int) Math.round(scale*marvinImage.getHeight());
        return doResize(width, newHeight);
    }

    @Override
    public ByteArrayOutputStream scaleByHeight(FrameProperties size) throws IOException {
        return scaleByHeight(size.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleByHeight(int height) throws IOException {
        return toInputStream(doScaleByHeight(height));
    }

    private MarvinImage doScaleByHeight(int height) {
        double scale = (double) height/marvinImage.getHeight();
        int newWidth = (int) Math.round(scale*marvinImage.getWidth());
        return doResize(newWidth, height);
    }

    @Override
    public ByteArrayOutputStream scaleDown(FrameProperties size) throws IOException {
        return scaleDown(size.getWidth(), size.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleDown(int width, int height) throws IOException {
        return toInputStream(doScaleDown(width, height));
    }

    private MarvinImage doScaleDown(int width, int height) {
        int iWidth = marvinImage.getWidth();
        int iHeight = marvinImage.getHeight();
        if (iHeight <= height && iWidth <= width) {
            return marvinImage;
        }
        if ((iHeight - height) >= (iWidth - width)) {
            return doScaleByHeight(height);
        } else {
            return doScaleByWidth(width);
        }
    }

    @Override
    public ByteArrayOutputStream scaleDownWithBackground(FrameProperties frame) throws IOException {
        return scaleDownWithBackground(frame.getWidth(), frame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleDownWithBackground(FrameProperties frame, Color bgColor) throws IOException {
        return scaleDownWithBackground(frame.getWidth(), frame.getHeight(), bgColor);
    }

    @Override
    public ByteArrayOutputStream scaleDownWithBackground(int width, int height) throws IOException {
        return scaleDownWithBackground(width, height, Color.WHITE);
    }

    @Override
    public ByteArrayOutputStream scaleDownWithBackground(int width, int height, Color bgColor) throws IOException {
        MarvinImage imgOut = doScaleDown(width, height);
        return toByteArrayOutputStream(withBackground(imgOut.getBufferedImageNoAlpha(), width, height, bgColor));
    }

    @Override
    public ByteArrayOutputStream scaleUp(FrameProperties frame) throws IOException {
        return scaleUp(frame.getWidth(), frame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleUp(int width, int height) throws IOException {
        return toInputStream(doScaleUp(width, height));
    }

    private MarvinImage doScaleUp(int width, int height) {
        int iWidth = marvinImage.getWidth();
        int iHeight = marvinImage.getHeight();
        if (iHeight >= height && iWidth >= width) {
            return marvinImage;
        }
        if ((iHeight - height) <= (iWidth - width)) {
            return doScaleByHeight(height);
        } else {
            return doScaleByWidth(width);
        }
    }

    @Override
    public ByteArrayOutputStream scaleUpAndCrop(FrameProperties frame) throws IOException {
        return scaleUpAndCrop(frame.getWidth(), frame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleUpAndCrop(int width, int height) throws IOException {
        MarvinImage imgOut = doScaleUp(width, height);
        if (imgOut.getWidth() > width || imgOut.getHeight() > height) {
            MarvinImage cropped = new MarvinImage(/* width, height */);
            MarvinPluginCollection.crop(imgOut, cropped,
                    (imgOut.getWidth() - width)/2, (imgOut.getHeight() - height)/2,
                    width, height);
            return toInputStream(cropped);
        }
        return toInputStream(imgOut);
    }

    private ByteArrayOutputStream toInputStream(MarvinImage image) throws IOException {
        return toByteArrayOutputStream(image.getBufferedImageNoAlpha());
    }

    private ByteArrayOutputStream toByteArrayOutputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, marvinImage.getFormatName(), outputStream);
        return outputStream;
    }

    private BufferedImage withBackground(BufferedImage imageIn, int width, int height, Color backgroundColor) {
        if (imageIn.getWidth() >= width && imageIn.getHeight() >= height) {
            return imageIn;
        }
        BufferedImage imageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imageOut.createGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);
        int x = (width - imageIn.getWidth())/2;
        int y = (height - imageIn.getHeight())/2;
        graphics.drawImage(imageIn, x, y, null);
        graphics.dispose();
        return imageOut;
    }

    @Override
    public void close() throws Exception {
        // ...
    }

}
