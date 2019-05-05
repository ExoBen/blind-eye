package org.toby.blindeye.bugs;

import KinectPV2.KinectPV2;
import processing.core.PImage;

public abstract class AbstractBug {

  public abstract PImage executeBug(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect);

}
