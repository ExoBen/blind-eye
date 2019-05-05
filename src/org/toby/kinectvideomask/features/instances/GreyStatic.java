package org.toby.kinectvideomask.features.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.Upscaler;
import org.toby.kinectvideomask.features.AbstractFeature;
import processing.core.PImage;

import static org.toby.kinectvideomask.UtilitiesAndConstants.KINECT_HEIGHT;
import static org.toby.kinectvideomask.UtilitiesAndConstants.KINECT_WIDTH;

public class GreyStatic extends AbstractFeature {

  public PImage executeFeature(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    return Upscaler.upscaler(kinect.getDepthImage(), KINECT_WIDTH*KINECT_HEIGHT);
  }

}