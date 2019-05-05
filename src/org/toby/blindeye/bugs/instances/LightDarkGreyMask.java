package org.toby.blindeye.bugs.instances;

import KinectPV2.KinectPV2;
import org.toby.blindeye.UtilitiesAndConstants;
import org.toby.blindeye.bugs.AbstractBug;
import processing.core.PImage;

public class LightDarkGreyMask extends AbstractBug {

  public LightDarkGreyMask() {}

  public PImage executeBug(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    return UtilitiesAndConstants.twoTone(body, -2302756, -14803426);
  }

}
