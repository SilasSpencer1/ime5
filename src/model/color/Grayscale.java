package model.color;

/**
 * Converts a color image into a grayscale image. A grayscale is composed only of shades of grey (if
 * the red, green, and blue values are the same).
 */
public class Grayscale extends AColorTransformation {

  @Override
  public boolean isGrayscale() {
    return true;
  }

  /**
   * Constructs a grayscale color transformation to be applied on an image.
   */
  public Grayscale() {
    if (isGrayscale()) {
      this.colorTransformation = new double[][]{
              {0, 0, 0},
              {0, 0, 0},
              {0, 0, 0}};
    }
  }

  @Override
  public boolean isGrayscaleGreen() {
    return false;
  }

  @Override
  public boolean isGrayscaleRed() {
    return false;
  }

  @Override
  public boolean isGrayscaleBlue() {
    return false;
  }

  @Override
  public boolean isGrayscaleValue() {
    return false;
  }

  @Override
  public boolean isGrayscaleLuma() {
    return false;
  }

  @Override
  public boolean isGrayscaleIntensity() {
    return false;
  }
}
