package org.toby.kinectvideomask.bugs.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.bugs.AbstractBug;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

import static org.toby.kinectvideomask.UtilitiesAndConstants.loadStatics;

public class DownloadedStatic extends AbstractBug {

  private Random rand;
  private ArrayList<PImage> statics;
  private Integer[] startingPoints = new Integer[] {0,3,6,9};
  private Integer startingPoint;
  private int frame = 0;

  public DownloadedStatic(PApplet p) {
    statics = loadStatics(p);
    rand = new Random();
    startingPoint = 0;
  }

  public PImage executeBug(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    frame++;
    if (frame%3 == 0) {
      frame = 0;
      startingPoint = startingPoints[rand.nextInt(4)];
    }
    return statics.get(startingPoint+frame);
  }



}
