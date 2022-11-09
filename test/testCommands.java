import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.CreateImageLayerCommand;
import controller.FlipHorizontallyCommand;
import controller.FlipVerticallyCommand;
import controller.GrayscaleCommand;
import controller.GrayscaleGreenCommand;
import controller.GrayscaleIntensityCommand;
import controller.GrayscaleRedCommand;
import controller.GrayscaleValueCommand;
import controller.IImageProcessingController;
import controller.LoadAllCommand;
import controller.LoadSingleCommand;
import controller.SaveAllCommand;
import controller.SaveSingleCommand;
import controller.SetCurrentCommand;
import java.io.File;
import model.color.Grayscale;
import model.color.GrayscaleGreen;
import model.color.GrayscaleIntensity;
import model.color.GrayscaleLuma;
import model.color.GrayscaleRed;
import model.color.GrayscaleValue;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.layer.ILayer;
import model.layer.ILayerModel;
import model.layer.Layer;
import model.layer.LayerModel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests that commands we give from the controller to the model
 * all work correctly and as intended.
 */
public class testCommands {

  IPixel[][] simpleTwoByTwoGrid;
  IPixel[][] simpleTwoByTwoGridHorizontalFlip;
  IPixel[][] simpleTwoByTwoGridVerticalFlip;
  IPixel[][] simpleTwoByTwoGridGrayscaleGreen;
  IPixel[][] simpleTwoByTwoGridGrayscaleRed;
  IPixel[][] simpleTwoByTwoGridGrayscaleBlue;
  IPixel[][] simpleTwoByTwoGridGrayscaleValue;
  IPixel[][] simpleTwoByTwoGridGrayscaleIntensity;
  IPixel[][] simpleTwoByTwoGridGrayscaleLuma;

  IImage twoByTwoImage;
  IImage twoByTwoImageHorizontalFlip;
  IImage twoByTwoImageVerticalFlip;
  IImage twoByTwoImageGrayscaleGreen;
  IImage twoByTwoImageGrayscaleRed;
  IImage twoByTwoImageGrayscaleBlue;
  IImage twoByTwoImageGrayscaleValue;
  IImage twoByTwoImageGrayscaleIntensity;
  IImage twoByTwoImageGrayscaleLuma;

  IImageProcessingController controller;
  Readable in;
  Appendable out;
  ILayerModel model;
  ILayer exLayer;
  ILayer exLayer2;
  ILayer exLayer3;
  IPixel[][] grid1;
  IPixel[][] grid2;
  IPixel[][] grid3;
  IPixel[][] grid4;
  IImage exImage;
  IImage exImage2;
  IImage exImage3;
  IImage exImage4;
  Appendable mockAppendable;

  private IPixel[][] sampleImageGrid1By2;

  @Before
  public void setUp() {

    // Goes in public void setUp()
    simpleTwoByTwoGrid = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 255, 0),
                    new Pixel(1, 0, 0, 0, 255), new Pixel(1, 1, 255, 255, 255)}};
    simpleTwoByTwoGridHorizontalFlip = new IPixel[][]{
            {new Pixel(0, 0, 0, 255, 0), new Pixel(0, 1, 255, 0, 0),
                    new Pixel(1, 0, 255, 255, 255), new Pixel(1, 1, 0, 0, 255)}};
    simpleTwoByTwoGridVerticalFlip = new IPixel[][]{
            {new Pixel(0, 0, 0, 0, 255), new Pixel(0, 1, 255, 255, 255),
                    new Pixel(1, 0, 255, 0, 0), new Pixel(1, 1, 0, 255, 0)}};
    simpleTwoByTwoGridGrayscaleGreen = new IPixel[][]{
            {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 255, 255, 255),
                    new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 255, 255)}};
    simpleTwoByTwoGridGrayscaleRed = new IPixel[][]{
            {new Pixel(0, 0, 255, 255, 255), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 255, 255)}};
    simpleTwoByTwoGridGrayscaleBlue = new IPixel[][]{
            {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 255, 255, 255),
                    new Pixel(1, 0, 255, 255, 255), new Pixel(1, 1, 255, 255, 255)}};
    simpleTwoByTwoGridGrayscaleValue = new IPixel[][]{
            {new Pixel(0, 0, 255, 255, 255), new Pixel(0, 1, 255, 255, 255),
                    new Pixel(1, 0, 255, 255, 255), new Pixel(1, 1, 255, 255, 255)}};
    simpleTwoByTwoGridGrayscaleIntensity = new IPixel[][]{
            {new Pixel(0, 0, 85, 85, 85), new Pixel(0, 1, 85, 85, 85),
                    new Pixel(1, 0, 85, 85, 85), new Pixel(1, 1, 255, 255, 255)}};
    simpleTwoByTwoGridGrayscaleLuma = new IPixel[][]{
            {new Pixel(0, 0, 54, 54, 54), new Pixel(0, 1, 182, 182, 182),
                    new Pixel(1, 0, 18, 18, 18), new Pixel(1, 1, 255, 255, 255)}};

    out = new StringBuilder();
    model = new LayerModel();
    exLayer = new Layer("first");
    exLayer2 = new Layer("second");
    exLayer3 = new Layer("third");
    grid1 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    grid2 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage = new Image(grid1, "Grid1");
    exImage2 = new Image(grid2, "Grid2");
    grid3 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage3 = new Image(grid3, "Grid3");
    grid4 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage4 = new Image(grid4, "Grid4");
    this.sampleImageGrid1By2 = new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
            new Pixel(0, 1, 50, 200, 10)}};

    // This goes in public void setUp() beneath the IPixel[][] grid definitions
    twoByTwoImage = new Image(simpleTwoByTwoGrid, "Default two by two grid");
    twoByTwoImageHorizontalFlip = new Image(simpleTwoByTwoGridHorizontalFlip, "Default two by two grid flipped horizontally");
    twoByTwoImageVerticalFlip = new Image(simpleTwoByTwoGridVerticalFlip, "Default two by two grid flipped vertically");
    twoByTwoImageGrayscaleGreen = new Image(simpleTwoByTwoGridGrayscaleGreen, "Default two by two grid with grayscale green applied");
    twoByTwoImageGrayscaleRed = new Image(simpleTwoByTwoGridGrayscaleRed, "Default two by two grid with grayscale red applied");
    twoByTwoImageGrayscaleBlue = new Image(simpleTwoByTwoGridGrayscaleBlue, "Default two by two grid with grayscale blue applied");
    twoByTwoImageGrayscaleValue = new Image(simpleTwoByTwoGridGrayscaleValue, "Default two by two grid with grayscale value applied");
    twoByTwoImageGrayscaleIntensity = new Image(simpleTwoByTwoGridGrayscaleIntensity, "Default two by two grid with grayscale intensity applied");
    twoByTwoImageGrayscaleLuma = new Image(simpleTwoByTwoGridGrayscaleLuma, "Two by two grid with grayscale luma applied");

    // This goes in public void setUp() beneath the Image definitions
    model.createImageLayer("twoByTwo");
    model.createImageLayer("twoByTwoHorizontalFlip");
    model.createImageLayer("twoByTwoVerticalFlip");
    model.createImageLayer("twoByTwoGrayscaleGreen");
    model.createImageLayer("twoByTwoGrayscaleRed");
    model.createImageLayer("twoByTwoGrayscaleBlue");
    model.createImageLayer("twoByTwoGrayscaleValue");
    model.createImageLayer("twoByTwoGrayscaleIntensity");
    model.createImageLayer("twoByTwoGrayscaleLuma");
    model.setCurrent("twoByTwo");
    model.loadLayer(twoByTwoImage);
    model.setCurrent("twoByTwoHorizontalFlip");
    model.loadLayer(twoByTwoImageHorizontalFlip);
    model.setCurrent("twoByTwoVerticalFlip");
    model.loadLayer(twoByTwoImageVerticalFlip);
    model.setCurrent("twoByTwoGrayscaleGreen");
    model.loadLayer(twoByTwoImageGrayscaleGreen);
    model.setCurrent("twoByTwoGrayscaleRed");
    model.loadLayer(twoByTwoImageGrayscaleRed);
    model.setCurrent("twoByTwoGrayscaleBlue");
    model.loadLayer(twoByTwoImageGrayscaleBlue);
    model.setCurrent("twoByTwoGrayscaleValue");
    model.loadLayer(twoByTwoImageGrayscaleValue);
    model.setCurrent("twoByTwoGrayscaleIntensity");
    model.loadLayer(twoByTwoImageGrayscaleIntensity);
    model.setCurrent("twoByTwoGrayscaleLuma");
    model.loadLayer(twoByTwoImageGrayscaleLuma);
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    model.setCurrent("first");
    mockAppendable = new StringBuilder();

  }


  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageLayerWithNullName() {
    new CreateImageLayerCommand(null).runCommand(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCreateImageLayer() {
    new CreateImageLayerCommand("Layer 2").runCommand(null);
  }

  @Test
  public void testCreateImageLayerCommand() {
    new CreateImageLayerCommand("fourth").runCommand(model);
    assertEquals(4, model.getLayers().size());
    assertTrue(model.getLayers().get(3).isVisible());
  }

  @Test
  public void testGrayscaleGreen() {
    setUp();
    model.setCurrent("twoByTwo");
    new GrayscaleGreenCommand().runCommand(model);
    IImage grayscaleTest = new GrayscaleGreen().apply(new Image(twoByTwoImage.getImage(), twoByTwoImage.getFilename()));

    IPixel[][] mutatedImage = grayscaleTest.getImage();
    assertEquals(mutatedImage[0][0].getRed(), simpleTwoByTwoGridGrayscaleGreen[0][0].getRed());
    assertEquals(mutatedImage[0][0].getGreen(), simpleTwoByTwoGridGrayscaleGreen[0][0].getGreen());
    assertEquals(mutatedImage[0][0].getBlue(), simpleTwoByTwoGridGrayscaleGreen[0][0].getBlue());
    assertEquals(mutatedImage[0][1].getRed(), simpleTwoByTwoGridGrayscaleGreen[0][1].getRed());
    assertEquals(mutatedImage[0][1].getGreen(), simpleTwoByTwoGridGrayscaleGreen[0][1].getGreen());
    assertEquals(mutatedImage[0][1].getBlue(), simpleTwoByTwoGridGrayscaleGreen[0][1].getBlue());
    assertEquals(mutatedImage[1][0].getRed(), simpleTwoByTwoGridGrayscaleGreen[1][0].getRed());
    assertEquals(mutatedImage[1][0].getGreen(), simpleTwoByTwoGridGrayscaleGreen[1][0].getGreen());
    assertEquals(mutatedImage[1][0].getBlue(), simpleTwoByTwoGridGrayscaleGreen[1][0].getBlue());
    assertEquals(mutatedImage[1][1].getRed(), simpleTwoByTwoGridGrayscaleGreen[1][1].getRed());
    assertEquals(mutatedImage[1][1].getGreen(), simpleTwoByTwoGridGrayscaleGreen[1][1].getGreen());
    assertEquals(mutatedImage[1][1].getBlue(), simpleTwoByTwoGridGrayscaleGreen[1][1].getBlue());
  }

  @Test
  public void testGrayscaleRed() {
    setUp();
    model.setCurrent("twoByTwo");
    new GrayscaleRedCommand().runCommand(model);
    IImage grayscaleTest = new GrayscaleRed().apply(new Image(twoByTwoImage.getImage(), twoByTwoImage.getFilename()));
    IPixel[][] mutatedImage = grayscaleTest.getImage();

    assertEquals(mutatedImage[0][0].getRed(), simpleTwoByTwoGridGrayscaleRed[0][0].getRed());
    assertEquals(mutatedImage[0][0].getGreen(), simpleTwoByTwoGridGrayscaleRed[0][0].getGreen());
    assertEquals(mutatedImage[0][0].getBlue(), simpleTwoByTwoGridGrayscaleRed[0][0].getBlue());
    assertEquals(mutatedImage[0][1].getRed(), simpleTwoByTwoGridGrayscaleRed[0][1].getRed());
    assertEquals(mutatedImage[0][1].getGreen(), simpleTwoByTwoGridGrayscaleRed[0][1].getGreen());
    assertEquals(mutatedImage[0][1].getBlue(), simpleTwoByTwoGridGrayscaleRed[0][1].getBlue());
    assertEquals(mutatedImage[1][0].getRed(), simpleTwoByTwoGridGrayscaleRed[1][0].getRed());
    assertEquals(mutatedImage[1][0].getGreen(), simpleTwoByTwoGridGrayscaleRed[1][0].getGreen());
    assertEquals(mutatedImage[1][0].getBlue(), simpleTwoByTwoGridGrayscaleRed[1][0].getBlue());
    assertEquals(mutatedImage[1][1].getRed(), simpleTwoByTwoGridGrayscaleRed[1][1].getRed());
    assertEquals(mutatedImage[1][1].getGreen(), simpleTwoByTwoGridGrayscaleRed[1][1].getGreen());
    assertEquals(mutatedImage[1][1].getBlue(), simpleTwoByTwoGridGrayscaleRed[1][1].getBlue());
  }

  @Test
  public void testGrayscaleValue() {
    setUp();
    model.setCurrent("twoByTwo");
    new GrayscaleValueCommand().runCommand(model);
    IImage grayscaleTest = new GrayscaleValue().apply(new Image(twoByTwoImage.getImage(), twoByTwoImage.getFilename()));
    IPixel[][] mutatedImage = grayscaleTest.getImage();

    assertEquals(mutatedImage[0][0].getRed(), simpleTwoByTwoGridGrayscaleValue[0][0].getRed());
    assertEquals(mutatedImage[0][0].getGreen(), simpleTwoByTwoGridGrayscaleValue[0][0].getGreen());
    assertEquals(mutatedImage[0][0].getBlue(), simpleTwoByTwoGridGrayscaleValue[0][0].getBlue());
    assertEquals(mutatedImage[0][1].getRed(), simpleTwoByTwoGridGrayscaleValue[0][1].getRed());
    assertEquals(mutatedImage[0][1].getGreen(), simpleTwoByTwoGridGrayscaleValue[0][1].getGreen());
    assertEquals(mutatedImage[0][1].getBlue(), simpleTwoByTwoGridGrayscaleValue[0][1].getBlue());
    assertEquals(mutatedImage[1][0].getRed(), simpleTwoByTwoGridGrayscaleValue[1][0].getRed());
    assertEquals(mutatedImage[1][0].getGreen(), simpleTwoByTwoGridGrayscaleValue[1][0].getGreen());
    assertEquals(mutatedImage[1][0].getBlue(), simpleTwoByTwoGridGrayscaleValue[1][0].getBlue());
    assertEquals(mutatedImage[1][1].getRed(), simpleTwoByTwoGridGrayscaleValue[1][1].getRed());
    assertEquals(mutatedImage[1][1].getGreen(), simpleTwoByTwoGridGrayscaleValue[1][1].getGreen());
    assertEquals(mutatedImage[1][1].getBlue(), simpleTwoByTwoGridGrayscaleValue[1][1].getBlue());
  }

  @Test
  public void testHorizontalFlip() {
    model.setCurrent("twoByTwo");
    new FlipHorizontallyCommand().runCommand(model);
    model.getCurrentLayer().getImage().flipImageH();
    IPixel[][] mutatedImage =  model.getCurrentLayer().getImage().getImage();

    assertEquals(mutatedImage[0][0], simpleTwoByTwoGridHorizontalFlip[0][0].getRed());
    assertEquals(mutatedImage[0][0].getGreen(), simpleTwoByTwoGridHorizontalFlip[0][0].getGreen());
    assertEquals(mutatedImage[0][0].getBlue(), simpleTwoByTwoGridHorizontalFlip[0][0].getBlue());
    assertEquals(mutatedImage[0][1].getRed(), simpleTwoByTwoGridHorizontalFlip[0][1].getRed());
    assertEquals(mutatedImage[0][1].getGreen(), simpleTwoByTwoGridHorizontalFlip[0][1].getGreen());
    assertEquals(mutatedImage[0][1].getBlue(), simpleTwoByTwoGridHorizontalFlip[0][1].getBlue());
    assertEquals(mutatedImage[1][0].getRed(), simpleTwoByTwoGridHorizontalFlip[1][0].getRed());
    assertEquals(mutatedImage[1][0].getGreen(), simpleTwoByTwoGridHorizontalFlip[1][0].getGreen());
    assertEquals(mutatedImage[1][0].getBlue(), simpleTwoByTwoGridHorizontalFlip[1][0].getBlue());
    assertEquals(mutatedImage[1][1].getRed(), simpleTwoByTwoGridHorizontalFlip[1][1].getRed());
    assertEquals(mutatedImage[1][1].getGreen(), simpleTwoByTwoGridHorizontalFlip[1][1].getGreen());
    assertEquals(mutatedImage[1][1].getBlue(), simpleTwoByTwoGridHorizontalFlip[1][1].getBlue());
  }

  @Test
  public void testVerticalFlip() {
    model.setCurrent("twoByTwo");
    new FlipVerticallyCommand().runCommand(model);
    model.getCurrentLayer().getImage().flipImageV();
    IPixel[][] mutatedImage = model.getCurrentLayer().getImage().getImage();

    assertEquals(mutatedImage[0][0].getRed(), simpleTwoByTwoGridVerticalFlip[0][0].getRed());
    assertEquals(mutatedImage[0][0].getGreen(), simpleTwoByTwoGridVerticalFlip[0][0].getGreen());
    assertEquals(mutatedImage[0][0].getBlue(), simpleTwoByTwoGridVerticalFlip[0][0].getBlue());
    assertEquals(mutatedImage[0][1].getRed(), simpleTwoByTwoGridVerticalFlip[0][1].getRed());
    assertEquals(mutatedImage[0][1].getGreen(), simpleTwoByTwoGridVerticalFlip[0][1].getGreen());
    assertEquals(mutatedImage[0][1].getBlue(), simpleTwoByTwoGridVerticalFlip[0][1].getBlue());
    assertEquals(mutatedImage[1][0].getRed(), simpleTwoByTwoGridVerticalFlip[1][0].getRed());
    assertEquals(mutatedImage[1][0].getGreen(), simpleTwoByTwoGridVerticalFlip[1][0].getGreen());
    assertEquals(mutatedImage[1][0].getBlue(), simpleTwoByTwoGridVerticalFlip[1][0].getBlue());
    assertEquals(mutatedImage[1][1].getRed(), simpleTwoByTwoGridVerticalFlip[1][1].getRed());
    assertEquals(mutatedImage[1][1].getGreen(), simpleTwoByTwoGridVerticalFlip[1][1].getGreen());
    assertEquals(mutatedImage[1][1].getBlue(), simpleTwoByTwoGridVerticalFlip[1][1].getBlue());
  }

  @Test
  public void testGrayscaleLuma() {
    setUp();
    model.setCurrent("twoByTwo");
    IImage grayscaleTest = new GrayscaleLuma().apply(new Image(twoByTwoImage.getImage(), twoByTwoImage.getFilename()));
    IPixel[][] mutatedImage = grayscaleTest.getImage();

    assertEquals(mutatedImage[0][0].getRed(), simpleTwoByTwoGridGrayscaleLuma[0][0].getRed());
    assertEquals(mutatedImage[0][0].getGreen(), simpleTwoByTwoGridGrayscaleLuma[0][0].getGreen());
    assertEquals(mutatedImage[0][0].getBlue(), simpleTwoByTwoGridGrayscaleLuma[0][0].getBlue());
    assertEquals(mutatedImage[0][1].getRed(), simpleTwoByTwoGridGrayscaleLuma[0][1].getRed());
    assertEquals(mutatedImage[0][1].getGreen(), simpleTwoByTwoGridGrayscaleLuma[0][1].getGreen());
    assertEquals(mutatedImage[0][1].getBlue(), simpleTwoByTwoGridGrayscaleLuma[0][1].getBlue());
    assertEquals(mutatedImage[1][0].getRed(), simpleTwoByTwoGridGrayscaleLuma[1][0].getRed());
    assertEquals(mutatedImage[1][0].getGreen(), simpleTwoByTwoGridGrayscaleLuma[1][0].getGreen());
    assertEquals(mutatedImage[1][0].getBlue(), simpleTwoByTwoGridGrayscaleLuma[1][0].getBlue());
    assertEquals(mutatedImage[1][1].getRed(), simpleTwoByTwoGridGrayscaleLuma[1][1].getRed());
    assertEquals(mutatedImage[1][1].getGreen(), simpleTwoByTwoGridGrayscaleLuma[1][1].getGreen());
    assertEquals(mutatedImage[1][1].getBlue(), simpleTwoByTwoGridGrayscaleLuma[1][1].getBlue());
  }

  @Test
  public void testGrayscaleIntensity() {
    setUp();
    model.setCurrent("twoByTwo");
    new GrayscaleIntensityCommand().runCommand(model);
    IImage grayscaleTest = new GrayscaleIntensity().apply(new Image(twoByTwoImage.getImage(), twoByTwoImage.getFilename()));
    IPixel[][] mutatedImage = grayscaleTest.getImage();

    assertEquals(mutatedImage[0][0].getRed(), simpleTwoByTwoGridGrayscaleIntensity[0][0].getRed());
    assertEquals(mutatedImage[0][0].getGreen(), simpleTwoByTwoGridGrayscaleIntensity[0][0].getGreen());
    assertEquals(mutatedImage[0][0].getBlue(), simpleTwoByTwoGridGrayscaleIntensity[0][0].getBlue());
    assertEquals(mutatedImage[0][1].getRed(), simpleTwoByTwoGridGrayscaleIntensity[0][1].getRed());
    assertEquals(mutatedImage[0][1].getGreen(), simpleTwoByTwoGridGrayscaleIntensity[0][1].getGreen());
    assertEquals(mutatedImage[0][1].getBlue(), simpleTwoByTwoGridGrayscaleIntensity[0][1].getBlue());
    assertEquals(mutatedImage[1][0].getRed(), simpleTwoByTwoGridGrayscaleIntensity[1][0].getRed());
    assertEquals(mutatedImage[1][0].getGreen(), simpleTwoByTwoGridGrayscaleIntensity[1][0].getGreen());
    assertEquals(mutatedImage[1][0].getBlue(), simpleTwoByTwoGridGrayscaleIntensity[1][0].getBlue());
    assertEquals(mutatedImage[1][1].getRed(), simpleTwoByTwoGridGrayscaleIntensity[1][1].getRed());
    assertEquals(mutatedImage[1][1].getGreen(), simpleTwoByTwoGridGrayscaleIntensity[1][1].getGreen());
    assertEquals(mutatedImage[1][1].getBlue(), simpleTwoByTwoGridGrayscaleIntensity[1][1].getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullGrayscale() {
    new GrayscaleCommand().runCommand(null);
  }

  @Test
  public void testGrayscaleCommand() {
    model.setCurrent("first");
    IImage grayImg = new Grayscale().apply(new Image(exImage.getImage(), exImage.getFilename()));
    assertArrayEquals(grayImg.getImage(), model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", model.getLayers().get(0).getImage().getFilename());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullLoadAllCommand() {
    new LoadAllCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelLoadAllCommand() {
    new LoadAllCommand("Tester1").runCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLoadSingleCommand() {
    new LoadSingleCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelLoadSingleCommand() {
    new LoadAllCommand("Tester2").runCommand(null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullSaveAllCommand() {
    new SaveAllCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSaveAllCommand() {
    SaveAllCommand saveAll = new SaveAllCommand("testing");
    File f = new File("testing");
    f.delete();
    saveAll.runCommand(null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSaveSingleCommand() {
    new SaveSingleCommand("file").runCommand(null);
  }



  @Test(expected = IllegalArgumentException.class)
  public void testNullSetCurrentCommand() {
    new SetCurrentCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSetCurrentCommand() {
    new SetCurrentCommand("third").runCommand(null);
  }

  @Test
  public void testSetCurrentCommand() {
    assertEquals("Layer #1, Name of Layer: first, Image Filename: Grid1, Visibility: true\n"
                    + "Layer #2, Name of Layer: second, Image Filename: Grid2, Visibility: true\n"
                    + "Layer #3, Name of Layer: third, Image Filename: Grid3, Visibility: true\n"
                    + "Number of valid layers created: 3\n"
                    + "Current Layer: Name of Layer: third, Image Filename: Grid3, Visibility: true\n",
            model.toString());
    new SetCurrentCommand("second").runCommand(model);
    assertEquals("Layer #1, Name of Layer: first, Image Filename: Grid1, Visibility: true\n"
                    + "Layer #2, Name of Layer: second, Image Filename: Grid2, Visibility: true\n"
                    + "Layer #3, Name of Layer: third, Image Filename: Grid3, Visibility: true\n"
                    + "Number of valid layers created: 3\n"
                    + "Current Layer: Name of Layer: second, Image Filename: Grid2, Visibility: true\n",
            model.toString());
  }

  @Test
  public void testBrighten() {
    model.loadLayer(twoByTwoImage);
    model.getCurrentLayer().brighten(1);
    assertEquals(model.getCurrentLayer().getImage().getImage(), simpleTwoByTwoGrid);
  }
}