package io.github.vatisteve.utils.image;

import java.io.IOException;
import java.io.InputStream;

/**
 * ImageTransformer
 *
 * @author  Steve
 * @since   Jun 09, 2023
 * 
 * <p>      Image transformations
 */
public interface ImageTransformer {

    InputStream resize(FrameProperties size) throws IOException;

    InputStream resize(int width, int height) throws IOException;

    InputStream scale(double scale) throws IOException;

    InputStream scaleByWidth(FrameProperties size) throws IOException;

    InputStream scaleByWidth(int width) throws IOException;

    InputStream scaleByHeight(FrameProperties size) throws IOException;

    InputStream scaleByHeight(int height) throws IOException;

    InputStream scaleDown(FrameProperties size) throws IOException;

    InputStream scaleDown(int width, int height) throws IOException;

    InputStream scaleDownWithBackground(FrameProperties frame) throws IOException;

    InputStream scaleDownWithBackground(int width, int height) throws IOException;

    InputStream scaleUp(FrameProperties frame) throws IOException;

    InputStream scaleUp(int width, int height) throws IOException;

    InputStream scaleUpAndCrop(FrameProperties frame) throws IOException;

    InputStream scaleUpAndCrop(int width, int height) throws IOException;

}
