package org.toby.kinectvideomask.features.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.Upscaler;
import org.toby.kinectvideomask.features.AbstractFeature;
import processing.core.PImage;

import static org.toby.kinectvideomask.UtilitiesAndConstants.KINECT_HEIGHT;
import static org.toby.kinectvideomask.UtilitiesAndConstants.KINECT_WIDTH;

public class DepthImage extends AbstractFeature {

  public PImage executeFeature(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    return Upscaler.upscaler(kinect.getDepthImage().get(39, 32, KINECT_WIDTH, KINECT_HEIGHT), KINECT_WIDTH*KINECT_HEIGHT);
  }

}
