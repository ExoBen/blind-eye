package org.toby.kinectvideomask.bugs.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.UtilitiesAndConstants;
import org.toby.kinectvideomask.bugs.AbstractBug;
import processing.core.PImage;

public class DarkLightGreyMask extends AbstractBug {

  public DarkLightGreyMask() {}

  public PImage executeBug(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    return UtilitiesAndConstants.twoTone(body, -14803426, -2302756);
  }

}
