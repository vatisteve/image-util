package io.github.vatisteve.utils.image;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;

/**
 * ImageTransformer
 *
 * @author  Steve
 * @since   Jun 09, 2023
 * 
 * <p>      Image transformations
 */
public interface ImageTransformer extends Closeable {

    /**
     * @param frame      The {@link FrameProperties} with specific expected width and height after resizing
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream resize(FrameProperties frame) throws IOException;

    /**
     * @param width     The expected width after resizing
     * @param height    The expected height after resizing
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream resize(int width, int height) throws IOException;

    /**
     * @param scale     The value to scale
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scale(double scale) throws IOException;

    /**
     * @param frame      The {@link FrameProperties} which has specific width
     * <p>              The image will have this width
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleByWidth(FrameProperties frame) throws IOException;

    /**
     * @param width     The expected width after scaling
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleByWidth(int width) throws IOException;

    /**
     * @param frame     The {@link FrameProperties} which has specific height
     * <p>              The image will have this height
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleByHeight(FrameProperties frame) throws IOException;

    /**
     * @param height      The expected height after scaling
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleByHeight(int height) throws IOException;

    /**
     * @param frame     The {@link FrameProperties} which has width and height
     * <p>              The image will be scale down and fit in the <code>frame</code>
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleDown(FrameProperties frame) throws IOException;

    /**
     * @param width     The width of the <code>frame</code>
     * @param height    The height of the <code>frame</code>
     * <p>              The image will be scale down and fit in the <code>frame</code>
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleDown(int width, int height) throws IOException;

    /**
     * @param frame     The {@link FrameProperties} which has width and height
     * <p>              The image will be scale down and fit in the <code>frame</code>
     * <p>              with default background color: <code>Color.WHITE</code>
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleDownWithBackground(FrameProperties frame) throws IOException;

    /**
     * @param frame     The {@link FrameProperties} which has width and height
     * @param bgColor   The background color, use {@link Color}
     * <p>              The image will be scale down and fit in the <code>frame</code>
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleDownWithBackground(FrameProperties frame, Color bgColor) throws IOException;

    /**
     * @param width     The width of the <code>frame</code>
     * @param height    The height of the <code>frame</code>
     * <p>              The image will be scale down and fit in the <code>frame</code>
     * <p>              with default background color: <code>Color.WHITE</code>
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleDownWithBackground(int width, int height) throws IOException;

    /**
     * @param width     The width of the <code>frame</code>
     * @param height    The height of the <code>frame</code>
     * @param bgColor   The background color, use {@link Color}
     * <p>              The image will be scale down and fit in the <code>frame</code>
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleDownWithBackground(int width, int height, Color bgColor) throws IOException;

    /**
     * @param frame     The {@link FrameProperties} which has width and height
     * @return          {@link ByteArrayOutputStream}
     * <p>              The image will be scaled up to have the width or height the same as min <code>frame</code> properties
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleUp(FrameProperties frame) throws IOException;

    /**
     * @param width     The width
     * @param height    The height
     * <p>              The image will be scaled up to have the width or height the same as min <code>frame</code> properties
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleUp(int width, int height) throws IOException;

    /**
     * @param frame     The {@link FrameProperties} which has width and height
     * <p>              The image will be scaled up
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleUpAndCrop(FrameProperties frame) throws IOException;

    /**
     * @param width     The width of the <code>frame</code>
     * @param height    The height of the <code>frame</code>
     * <p>              The image will be scaled up and center crop to entire <code>frame</code>
     * @return          {@link ByteArrayOutputStream}
     * @throws IOException when writing the image to output occur error
     */
    ByteArrayOutputStream scaleUpAndCrop(int width, int height) throws IOException;

}
