package model.color;

import model.image.Pixel;

/**
 * Converts a color image into a grayscale image. A grayscale is composed only of shades of grey (if
 * the red, green, and blue values are the same).
 */
public class GrayscaleGreen extends AColorTransformation  {

  private Pixel pixel;

  /**
   * Constructs a grayscale color transformation to be applied on an image.
   */
  public GrayscaleGreen() {

    this.colorTransformation = new double[][]{
            {.2126, .7512, 1},
            {.2126, .7512, 1},
            {.2126, .7512, 1}};
  }

  @Override
  public boolean isGrayscaleGreen() {
    return true;
  }
}