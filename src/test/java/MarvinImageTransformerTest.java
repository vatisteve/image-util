import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import io.github.vatisteve.utils.image.ImageTransformer;
import io.github.vatisteve.utils.image.ImageTransformerFactory;
import io.github.vatisteve.utils.image.MimeTypeNotSupportedException;

public class MarvinImageTransformerTest {

    public static void main(String[] args) throws MimeTypeNotSupportedException, IOException, Exception {
        InputStream in1 = new FileInputStream("src/test/resources/height_longer.jpg");
        try (ImageTransformer trans = ImageTransformerFactory.buildImageTransformer(null, in1, "jpg")) {
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleDownWithBackground(500, 500).toByteArray()))
                    , "jpg", new File("target/scaleDownWithBackground1.jpg"));
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleUpAndCrop(1000, 1000).toByteArray()))
                    , "jpg", new File("target/scaleUpAndCrop1.jpg"));
        }
        InputStream in2 = new FileInputStream("src/test/resources/width_longer.jpg");
        try (ImageTransformer trans = ImageTransformerFactory.buildImageTransformer(null, in2, "jpg")) {
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleDownWithBackground(500, 500).toByteArray()))
                    , "jpg", new File("target/scaleDownWithBackground2.jpg"));
            ImageIO.write(ImageIO.read(
                    new ByteArrayInputStream(trans.scaleUpAndCrop(1000, 1000).toByteArray()))
                    , "jpg", new File("target/scaleUpAndCrop2.jpg"));
        }
        System.out.println("done!");
    }

}
