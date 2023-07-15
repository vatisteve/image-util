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

import io.github.vatisteve.utils.image.DimensionType;
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
    public ByteArrayOutputStream resize(FrameProperties frame) throws IOException {
        if (isRatioFrame(frame)) return toOutputStream(marvinImage);
        return resize(frame.getWidth(), frame.getHeight());
    }

    @Override
    public ByteArrayOutputStream resize(int width, int height) throws IOException {
        return toOutputStream(doResize(width, height));
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
    public ByteArrayOutputStream scaleByWidth(FrameProperties frame) throws IOException {
        if (isRatioFrame(frame)) return toOutputStream(marvinImage);
        return scaleByWidth(frame.getWidth());
    }

    @Override
    public ByteArrayOutputStream scaleByWidth(int width) throws IOException {
        return toOutputStream(doScaleByWidth(width));
    }

    private MarvinImage doScaleByWidth(int width) {
        double scale = (double) width/marvinImage.getWidth();
        int newHeight = (int) Math.round(scale*marvinImage.getHeight());
        return doResize(width, newHeight);
    }

    @Override
    public ByteArrayOutputStream scaleByHeight(FrameProperties frame) throws IOException {
        if (isRatioFrame(frame)) return toOutputStream(marvinImage);
        return scaleByHeight(frame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleByHeight(int height) throws IOException {
        return toOutputStream(doScaleByHeight(height));
    }

    private MarvinImage doScaleByHeight(int height) {
        double scale = (double) height/marvinImage.getHeight();
        int newWidth = (int) Math.round(scale*marvinImage.getWidth());
        return doResize(newWidth, height);
    }

    @Override
    public ByteArrayOutputStream scaleDown(FrameProperties frame) throws IOException {
        frame = detectScaleDownFrame(frame);
        return scaleDown(frame.getWidth(), frame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleDown(int width, int height) throws IOException {
        return toOutputStream(doScaleDown(width, height));
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
        FrameProperties newFrame = detectScaleDownFrame(frame);
        return scaleDownWithBackground(newFrame.getWidth(), newFrame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleDownWithBackground(FrameProperties frame, Color bgColor) throws IOException {
        FrameProperties newFrame = detectScaleDownFrame(frame);
        return scaleDownWithBackground(newFrame.getWidth(), newFrame.getHeight(), bgColor);
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
        FrameProperties newFrame = detectScaleUpFrame(frame);
        return scaleUp(newFrame.getWidth(), newFrame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleUp(int width, int height) throws IOException {
        return toOutputStream(doScaleUp(width, height));
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
        FrameProperties newFrame = detectScaleUpFrame(frame);
        return scaleUpAndCrop(newFrame.getWidth(), newFrame.getHeight());
    }

    @Override
    public ByteArrayOutputStream scaleUpAndCrop(int width, int height) throws IOException {
        MarvinImage imgOut = doScaleUp(width, height);
        if (imgOut.getWidth() > width || imgOut.getHeight() > height) {
            MarvinImage cropped = new MarvinImage(/* width, height */);
            MarvinPluginCollection.crop(imgOut, cropped,
                    (imgOut.getWidth() - width)/2, (imgOut.getHeight() - height)/2,
                    width, height);
            return toOutputStream(cropped);
        }
        return toOutputStream(imgOut);
    }

    private ByteArrayOutputStream toOutputStream(MarvinImage image) throws IOException {
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

    /**
     * @param   frame the frame
     * @return  the smaller frame
     */
    private FrameProperties detectScaleUpFrame(FrameProperties frame) {
        if (!frame.getDimensionType().equals(DimensionType.RATIO)) return frame;
        int rWidth = frame.getWidth();
        int rHeight = frame.getHeight();
        int iWidth = marvinImage.getWidth();
        int iHeight = marvinImage.getHeight();
        int width = 0;
        int height = 0;
        if (rWidth == rHeight) {
            width = height = iWidth >= iHeight ? iHeight : iWidth;
        } else if (rWidth < rHeight) {
            height = iHeight;
            width = iHeight*rWidth/rHeight;
        } else {
            width = iWidth;
            height = iWidth*rHeight/rWidth;
        }
        final int w = width;
        final int h = height;
        return new FrameProperties() {
            @Override
            public int getWidth() {
                return w;
            }
            @Override
            public int getHeight() {
                return h;
            }
            @Override
            public DimensionType getDimensionType() {
                return null;
            }
        };
    }

    /**
     * @param   frame the frame
     * @return  the bigger frame
     */
    private FrameProperties detectScaleDownFrame(FrameProperties frame) {
        if (!frame.getDimensionType().equals(DimensionType.RATIO)) return frame;
        int rWidth = frame.getWidth();
        int rHeight = frame.getHeight();
        int iWidth = marvinImage.getWidth();
        int iHeight = marvinImage.getHeight();
        int width = 0;
        int height = 0;
        if (rWidth == rHeight) {
            width = height = iWidth >= iHeight ? iWidth : iHeight;
        } else if (rWidth < rHeight) {
            width = iWidth;
            height = iWidth*rHeight/rWidth;
        } else {
            height = iHeight;
            width = iHeight*rWidth/rHeight;
        }
        final int w = width;
        final int h = height;
        return new FrameProperties() {
            @Override
            public int getWidth() {
                return w;
            }
            @Override
            public int getHeight() {
                return h;
            }
            @Override
            public DimensionType getDimensionType() {
                return null;
            }
        };
    }

    private boolean isRatioFrame(FrameProperties frame) {
        return DimensionType.RATIO.equals(frame.getDimensionType());
    }

    @Override
    public void close() throws IOException {
        // ...
    }

}
