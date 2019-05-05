package org.toby.kinectvideomask.features.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.features.AbstractFeature;
import processing.core.PImage;

import java.util.Random;

import static org.toby.kinectvideomask.UtilitiesAndConstants.BLACK;
import static org.toby.kinectvideomask.UtilitiesAndConstants.WHITE;

public class BitmapFuzz extends AbstractFeature {

  private Random rand;

  public BitmapFuzz() {
    rand = new Random();
  }

  public PImage executeFeature(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    liveVideo.loadPixels();
    int area = (body.width * body.height);
    for (int i = 0; i < area; i++) {
      int willItChange = rand.nextInt(3);
      if (body.pixels[i] == BLACK && willItChange == 0) {
        liveVideo.pixels[i] = (rand.nextInt(2) == 0 ? BLACK : WHITE);
      }
    }
    liveVideo.updatePixels();
    return liveVideo;
  }

}
