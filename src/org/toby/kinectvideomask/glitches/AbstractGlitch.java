package org.toby.kinectvideomask.glitches;

import KinectPV2.KinectPV2;
import processing.core.PImage;

public abstract class AbstractGlitch {

  public abstract PImage executeGlitch(PImage liveVideo, PImage body, KinectPV2 kinect);

}
