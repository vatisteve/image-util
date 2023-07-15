import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import io.github.vatisteve.utils.image.DimensionType;
import io.github.vatisteve.utils.image.FrameProperties;
import io.github.vatisteve.utils.image.ImageTransformer;
import io.github.vatisteve.utils.image.ImageTransformerFactory;
import io.github.vatisteve.utils.image.MimeTypeNotSupportedException;

public class MarvinImageTransformerTest {

    public static void main(String[] args) throws MimeTypeNotSupportedException, IOException, Exception {
        InputStream in1 = new FileInputStream("src/test/resources/height_longer.jpg");
        try (ImageTransformer trans = ImageTransformerFactory.buildImageTransformer(null, in1, "jpg")) {
            FrameProperties frame = new FrameProperties() {
                @Override
                public int getWidth() {
                    return 500;
                }
                @Override
                public int getHeight() {
                    return 600;
                }
                @Override
                public DimensionType getDimensionType() {
                    return DimensionType.PIXEL;
                }
            };
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleDownWithBackground(frame).toByteArray()))
                    , "jpg", new File("target/scaleDownWithBackground1.jpg"));
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleUpAndCrop(frame).toByteArray()))
                    , "jpg", new File("target/scaleUpAndCrop1.jpg"));
        }
        InputStream in2 = new FileInputStream("src/test/resources/width_longer.jpg");
        try (ImageTransformer trans = ImageTransformerFactory.buildImageTransformer(null, in2, "jpg")) {
            FrameProperties frame = new FrameProperties() {
                @Override
                public int getWidth() {
                    return 2;
                }
                @Override
                public int getHeight() {
                    return 1;
                }
                @Override
                public DimensionType getDimensionType() {
                    return DimensionType.RATIO;
                }
            };
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleDownWithBackground(frame, Color.GRAY).toByteArray()))
                    , "jpg", new File("target/scaleDownWithBackground2.jpg"));
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleUpAndCrop(frame).toByteArray()))
                    , "jpg", new File("target/scaleUpAndCrop2.jpg"));
        }
        System.out.println("done!");
    }

}
