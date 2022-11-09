package model.image;


import java.util.Arrays;
import java.util.Objects;

import utils.ImageUtil;

/**
 * Represents an image that is of the PPM/JPEG/PNG format (a simple, text-based file format to store
 * images) that has a list of pixels. A PPM Image is made up of pixels that have red, green, and
 * blue values of each pixel, row-wise.
 */
public class Image implements IImage {

  private final IPixel[][] image;
  private final String filename;

  /**
   * Constructs a {@code Image} object based on a given file for the purpose of loading an image.
   *
   * @param filename the name of a given file
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null or if the image is null because of a nonexistent file
   */
  public Image(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("The filename cannot be null.");
    }
    this.filename = filename;
    this.image = ImageUtil.readPPM(this.filename);

    if (this.image == null) {
      throw new IllegalArgumentException("The file doesn't exist.");
    }
  }

  /**
   * Constructs a {@code Image} object with the given name based on a given set of pixels for the
   * purpose of creating an image.
   *
   * @param image    the image to be loaded
   * @param filename the name of the image
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null
   */
  public Image(IPixel[][] image, String filename) {
    if (image == null || filename == null || image.length == 0 || image[0].length == 0
            || this.checkImageGrid(image)) {
      throw new IllegalArgumentException("Cannot have a null/empty image or filename.");
    }

    this.image = image;
    this.filename = filename;
  }

  /**
   * Returns true if any of the pixels in a given 2D IPixel array are invalid.
   *
   * @param imageGrid the given 2D IPixel array
   * @return true if any of the pixels in the given 2D IPixel array are invalid, false if they are
   * all valid
   */
  private boolean checkImageGrid(IPixel[][] imageGrid) throws IllegalArgumentException {
    for (IPixel[] iPixels : imageGrid) {
      for (IPixel iPixel : iPixels) {
        if (iPixel == null) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * gets the Image as a 2D array of pixels.
   *
   * @return IPixel[][].
   */

  @Override
  public IPixel[][] getImage() {
    IPixel[][] imageGrid = new IPixel[this.getHeight()][this.getWidth()];

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        IPixel currPixel = this.image[i][j];
        imageGrid[i][j] = new Pixel(i, j, currPixel.getRed(), currPixel.getGreen(),
                currPixel.getBlue());
      }
    }

    return imageGrid;
  }

  /**
   * flips the image at the current layer horizontally.
   */

  @Override
  public void flipImageH() {

    for (int row = 0; row < this.image.length; row++) { // Each column is accessed through each row
      for (int col = 0; col < this.image[0].length / 2; col++) { // Access each column for half of the image
        IPixel temp = this.image[row][(this.image[0].length) - col - 1]; // Holds the opposite value to swap
        this.image[row][this.image[0].length - col - 1] = this.image[row][col]; // Puts current entry into 'opposite position'
        this.image[row][col] = temp; // Sets the current entry to that of the 'opposite position'
      }
    }
  }


  /**
   * gets the filename of an image.
   *
   * @return String filename.
   */
  @Override
  public String getFilename() {
    return this.filename;
  }

  /**
   * gets the height of an image.
   *
   * @return height. (1024 x 768) -> returns 768
   */

  @Override
  public int getHeight() {
    return this.image.length;
  }

  /**
   * gets the width of an image.
   *
   * @return height. (1024 x 768) -> returns 1024
   */


  @Override
  public int getWidth() {
    return this.image[0].length;
  }

  /**
   * flips a given image vertically.
   */
  @Override
  public void flipImageV() {

    for (int row = 0; row < this.image.length / 2; row++) { // Working one row at a time and only do half the image!!!
      IPixel[] temp = this.image[(this.image.length) - row - 1]; // Collect the temp row from the 'other side' of the array
      this.image[this.image.length - row - 1] = this.image[row]; // Put the current row in the row on the 'other side' of the array
      this.image[row] = temp; // Now put the row from the other side in the current row
    }
  }

  protected double[][] colorTransformation;

  /**
   * Applies a certain method or function to an image.
   *
   * @param image the image at the current layer.
   * @return the image after having a modification done to it
   * @throws IllegalArgumentException if the image or method params are null.
   */

  @Override
  public IImage apply(IImage image) throws IllegalArgumentException {
    if (image == null || colorTransformation == null) {
      throw new IllegalArgumentException("Argument(s) not be null!");
    }
    IPixel[][] imageGrid = image.getImage();

    for (int i = 0; i < imageGrid.length; i++) {
      for (int j = 0; j < imageGrid[i].length; j++) {
        imageGrid[i][j] = applyToEachPixel(new Pixel(imageGrid[i][j].getX(), imageGrid[i][j].getY(),
                imageGrid[i][j].getRed(), imageGrid[i][j].getGreen(), imageGrid[i][j].getBlue()));
      }
    }

    return new Image(imageGrid, image.getFilename());
  }

  /**
   * makes sure an image's color property cannot go above 255 or below 0.
   *
   * @param result the color intensity.
   * @return the color intensity clamped.
   */
  protected int clampValues(int result) {
    if (result > 255) {
      return 255;
    } else {
      return Math.max(result, 0);
    }
  }

  /**
   * Apply the color transformation to the given pixel in which the final red, green, and blue
   * values of a pixel are combinations of its initial red, green, and blue values. Clamping is
   * applied if needed.
   *
   * @param pix a given pixel in an image
   * @return the pixel with the color transformation applied to it
   * @throws IllegalArgumentException if the given pixel is null
   */
  protected IPixel applyToEachPixel(IPixel pix) throws IllegalArgumentException {
    if (pix == null) {
      throw new IllegalArgumentException("Cannot have a null pixel.");
    }
    int[] rgb = {pix.getRed(), pix.getGreen(), pix.getBlue()};
    int[] result = new int[3];

    for (int i = 0; i < colorTransformation.length; i++) {
      for (int j = 0; j < rgb.length; j++) {
        result[i] += colorTransformation[i][j] * rgb[j];
      }
      result[i] = this.clampValues(result[i]);
    }

    return new Pixel(pix.getX(), pix.getY(), result[0], result[1], result[2]);
  }


  /**
   * gets the blue component of an image.
   */
  @Override
  public void GrayscaleBlue() {

    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        IPixel thisPixel = this.image[row][col];
        this.image[row][col].setGreen(thisPixel.getBlue());
        this.image[row][col].setRed(thisPixel.getBlue());
      }
    }
  }

  /**
   * gets the red component of an image.
   */
  @Override
  public void grayscaleRed() {

    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        IPixel thisPixel = this.image[row][col];
        this.image[row][col].setGreen(thisPixel.getRed());
        this.image[row][col].setBlue(thisPixel.getRed());
      }
    }
  }

  /**
   * gets the green component of an image.
   */
  @Override
  public void GrayscaleGreen() {
    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        IPixel thisPixel = this.image[row][col];
        this.image[row][col].setRed(thisPixel.getGreen());
        this.image[row][col].setBlue(thisPixel.getGreen());
      }
    }
  }

  /**
   * grayscales the image via the intensity.
   */
  @Override
  public void greyscaleIntensity() {

    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        IPixel thisPixel = this.image[row][col];
        this.image[row][col].setRed((int) thisPixel.getIntensity());
        this.image[row][col].setGreen((int) thisPixel.getIntensity());
        this.image[row][col].setBlue((int) thisPixel.getIntensity());
      }
    }
  }

  /**
   * brightens the image by a color value.
   *
   * @param b a user given color value.
   */
  @Override
  public void bImage(int b) {
    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        IPixel thisPixel = this.image[row][col];
        if (thisPixel.getRed() + clampValues(b) > 255) {
          this.image[row][col].setRed(255);
        } else {
          this.image[row][col].setRed(Math.max(thisPixel.getRed() + b, 0));
        }
        if (thisPixel.getBlue() + clampValues(b) > 255) {
          this.image[row][col].setBlue(255);
        } else {
          this.image[row][col].setBlue(Math.max(thisPixel.getBlue() + b, 0));
        }
        if (thisPixel.getGreen() + clampValues(b) > 255) {
          this.image[row][col].setGreen(255);
        } else {
          this.image[row][col].setGreen(Math.max(thisPixel.getGreen() + b, 0));
        }
      }
    }
  }


  /**
   * override equals.
   *
   * @param o any object. probably an image.
   * @return boolean if two objects are equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image ppmImage = (Image) o;
    return Arrays.deepEquals(image, ppmImage.image) && Objects
            .equals(filename, ppmImage.filename);
  }

  /**
   * overriding hashcode.
   *
   * @return new hashcode.
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(filename);
    result = 31 * result + Arrays.deepHashCode(image);
    return result;
  }
}
