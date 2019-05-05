package org.toby.kinectvideomask.features.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.features.AbstractFeature;
import processing.core.PImage;

import static processing.core.PConstants.THRESHOLD;

public class ShowThePast extends AbstractFeature {

  private int counter = 0;
  private PImage[] savedFrame = new PImage[50];

  public ShowThePast() {}

  public PImage executeFeature(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    PImage outputVideo;
    if (savedFrame[counter] != null) {
      outputVideo = savedFrame[counter];
    } else {
      outputVideo = liveVideo;
      outputVideo.filter(THRESHOLD);
    }
    savedFrame[counter] = liveVideo;
    counter++;
    if (counter >= 50) {
      counter = 0;
    }
    return outputVideo;
  }
}
