package model.color;

import model.image.Pixel;

/**
 * Converts a color image into a grayscale image. A grayscale is composed only of shades of grey (if
 * the red, green, and blue values are the same).
 */
public class GrayscaleRed extends AColorTransformation  {

  private Pixel pixel;

  /**
   * Constructs a grayscale color transformation to be applied on an image.
   */
  public GrayscaleRed() {

    this.colorTransformation = new double[][]{
            {1, .7512, .0722},
            {1, .7512, .0722},
            {1, .7512, .0722}};
  }

  @Override
  public boolean isGrayscaleRed() {
    return true;
  }
}