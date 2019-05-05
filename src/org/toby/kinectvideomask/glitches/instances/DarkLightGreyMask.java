package org.toby.kinectvideomask.glitches.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.UtilitiesAndConstants;
import org.toby.kinectvideomask.glitches.AbstractGlitch;
import processing.core.PImage;

public class DarkLightGreyMask extends AbstractGlitch {

  public DarkLightGreyMask() {}

  public PImage executeGlitch(PImage liveVideo, PImage body, KinectPV2 kinect) {
    return UtilitiesAndConstants.twoTone(body, -14803426, -2302756);
  }

}
