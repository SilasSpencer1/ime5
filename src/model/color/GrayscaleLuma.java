package model.color;

/**
 * Converts a color image into a grayscale image. A grayscale is composed only of shades of grey (if
 * the red, green, and blue values are the same).
 */
public class GrayscaleLuma extends AColorTransformation {

  /**
   * Constructs a grayscale color transformation to be applied on an image.
   */
  public GrayscaleLuma() {
    this.colorTransformation = new double[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}};
  }

  @Override
  public boolean isGrayscaleLuma() {
    return true;
  }
}
