package controller;
import model.layer.ILayerModel;

public class FlipHorizontallyCommand implements IPhotoCommands {

  public FlipHorizontallyCommand() {}

  /**
   * Uses the given model to perform the class' command as a way of communicating to the model.
   *
   * @param m the model of the image-processing multi-layered model
   * @throws IllegalArgumentException if the given model is null or if any parameters produce
   *                                  invalid results (i.e. invalid filename)
   */

  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    m.getCurrentLayer().flippedImageH();
  }
}
