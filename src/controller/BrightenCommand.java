package controller;

import model.layer.ILayerModel;

public class BrightenCommand implements IPhotoCommands {
  private final String brightnessLevel;

  public BrightenCommand(String brightnessLevel) {
    this.brightnessLevel = brightnessLevel;
  }

  /**
   * Uses model to do a specific command and sends it modifications back to the model.
   *
   * @param m the model of the image processor.
   * @throws IllegalArgumentException if model is null or if any parameters produce
   *                                  invalid results (i.e. invalid filename)
   */

  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    System.out.println(Integer.parseInt(brightnessLevel));
    m.getCurrentLayer().brighten(Integer.parseInt(brightnessLevel));
  }
}
