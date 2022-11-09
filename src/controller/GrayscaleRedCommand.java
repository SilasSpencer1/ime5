package controller;

import model.color.AColorTransformation;
import model.color.Grayscale;
import model.layer.ILayerModel;

/**
 * A class representing the command to make an image with a grayscale model.filter.
 */
public class GrayscaleRedCommand implements IPhotoCommands {

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }

    m.getCurrentLayer().grayscaleRedComponent();

    }
  }