package org.toby.blindeye.bugs.instances;

import KinectPV2.KinectPV2;
import org.toby.blindeye.UtilitiesAndConstants;
import org.toby.blindeye.bugs.AbstractBug;
import processing.core.PImage;

import static org.toby.blindeye.UtilitiesAndConstants.BLACK;
import static org.toby.blindeye.UtilitiesAndConstants.WHITE;

public class BlackAndWhiteMask extends AbstractBug {

  public BlackAndWhiteMask() {}

  public PImage executeBug(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    return UtilitiesAndConstants.twoTone(body, WHITE, BLACK);
  }

}
