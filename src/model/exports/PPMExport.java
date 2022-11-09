package model.exports;

import java.io.IOException;
import java.io.Writer;
import model.image.IImage;
import model.image.IPixel;

/**
 * Represents a class which manages a given {@code IImage} and exports the Image based on this
 * object's {@link Writer} to PPM format.
 */
public class PPMExport implements IExport {

  private final IImage image;
  private final Writer wr;

  /**
   * Constructs a {@code PPMOutputFileManager} object with a given Writer.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if any argument is null
   */
  public PPMExport(IImage image, Writer wr) {
    if (image == null || wr == null) {
      throw new IllegalArgumentException("Cannot have any null arguments.");
    }
    this.image = image;
    this.wr = wr;
  }

  @Override
  public void export() throws IOException {
    int height = image.getHeight();
    int width = image.getWidth();

    // write the header
    this.wr.write(String.format("P3 %d %d %d\n", width, height, 255));

    IPixel[][] imageGrid = image.getImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.wr.write(imageGrid[i][j].toString() + " ");
      }
    }

    this.wr.close();
  }
}