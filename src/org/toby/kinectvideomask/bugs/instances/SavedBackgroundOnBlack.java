package org.toby.kinectvideomask.bugs.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.bugs.AbstractBug;
import processing.core.PImage;

import static org.toby.kinectvideomask.UtilitiesAndConstants.BLACK;

public class SavedBackgroundOnBlack extends AbstractBug {

  public PImage executeBug(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    PImage mask = new PImage(body.width, body.height);
    int bodyUpscaledSize = (body.width * body.height);
    for (int i = 0; i < bodyUpscaledSize; i++) {
      if (body.pixels[i] == BLACK) {
        mask.pixels[i] = savedBackground.pixels[i];
      } else {
        mask.pixels[i] = BLACK;
      }
    }
    return mask;
  }

}
