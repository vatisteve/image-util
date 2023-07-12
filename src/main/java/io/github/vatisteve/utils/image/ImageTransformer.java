package io.github.vatisteve.utils.image;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ImageTransformer
 *
 * @author  Steve
 * @since   Jun 09, 2023
 * 
 * <p>      Image transformations
 */
public interface ImageTransformer extends AutoCloseable {

    ByteArrayOutputStream resize(FrameProperties size) throws IOException;

    ByteArrayOutputStream resize(int width, int height) throws IOException;

    ByteArrayOutputStream scale(double scale) throws IOException;

    ByteArrayOutputStream scaleByWidth(FrameProperties size) throws IOException;

    ByteArrayOutputStream scaleByWidth(int width) throws IOException;

    ByteArrayOutputStream scaleByHeight(FrameProperties size) throws IOException;

    ByteArrayOutputStream scaleByHeight(int height) throws IOException;

    ByteArrayOutputStream scaleDown(FrameProperties size) throws IOException;

    ByteArrayOutputStream scaleDown(int width, int height) throws IOException;

    ByteArrayOutputStream scaleDownWithBackground(FrameProperties frame) throws IOException;

    ByteArrayOutputStream scaleDownWithBackground(FrameProperties frame, Color bgColor) throws IOException;

    ByteArrayOutputStream scaleDownWithBackground(int width, int height) throws IOException;

    ByteArrayOutputStream scaleDownWithBackground(int width, int height, Color bgColor) throws IOException;

    ByteArrayOutputStream scaleUp(FrameProperties frame) throws IOException;

    ByteArrayOutputStream scaleUp(int width, int height) throws IOException;

    ByteArrayOutputStream scaleUpAndCrop(FrameProperties frame) throws IOException;

    ByteArrayOutputStream scaleUpAndCrop(int width, int height) throws IOException;

}
