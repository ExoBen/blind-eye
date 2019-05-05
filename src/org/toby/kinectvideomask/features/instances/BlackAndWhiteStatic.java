package org.toby.kinectvideomask.features.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.Upscaler;
import org.toby.kinectvideomask.features.AbstractFeature;
import processing.core.PConstants;
import processing.core.PImage;

import static org.toby.kinectvideomask.UtilitiesAndConstants.KINECT_HEIGHT;
import static org.toby.kinectvideomask.UtilitiesAndConstants.KINECT_WIDTH;

public class BlackAndWhiteStatic extends AbstractFeature {

  public PImage executeFeature(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    PImage depth = Upscaler.upscaler(kinect.getDepthImage(), KINECT_WIDTH*KINECT_HEIGHT);
    depth.filter(PConstants.THRESHOLD, 0.5f);
    return depth;
  }

}
