package org.toby.kinectvideomask.features;

import KinectPV2.KinectPV2;
import processing.core.PImage;

public abstract class AbstractFeature {

  public abstract PImage executeFeature(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect);

}
