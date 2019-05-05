package org.toby.kinectvideomask;

import KinectPV2.KinectPV2;
import processing.core.PApplet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.toby.kinectvideomask.UtilitiesAndConstants.*;

class TextOverlay extends VideoMaskDegree {

  private PApplet parent;
  private Random rand;

  TextOverlay(PApplet p) {
    parent = p;
    rand = new Random();
  }

  void info(long currentTime, KinectPV2 kinect) {
    parent.textSize(48);
    parent.fill(255);
//    parent.text("Time: " + intoSeconds(currentTime) + "s", LEFT_OFFSET + 50, 170);
    parent.text("FPS: " + floor(parent.frameRate), LEFT_OFFSET + 50, 220);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy\nHH:mm:ss");
    String dateTime = sdf.format(new Date());
    parent.text(dateTime, RIGHT_DISPLAY_OFFSET - 270, 995);

    parent.text("REC", LEFT_OFFSET + 50, 70);
    if (floor(currentTime/1000) % 2 == 0) {
      parent.fill(255, 0, 0);
      parent.stroke(255, 0, 0);
      parent.rect(LEFT_OFFSET + 150, 39, 16, 24);
      parent.rect(LEFT_OFFSET + 146, 43, 24, 16);
    }
    parent.fill(255);
    parent.text(kinect.getBodyTrackUser().size(), RIGHT_DISPLAY_OFFSET -70, 70);
  }

  void pauseScreen(long currentTime, boolean loading) {
    if (loading) {
      parent.textSize(48);
      parent.fill(255);
      parent.text("LOAD", LEFT_OFFSET + 50, 70);
    } else {
      if (floor(currentTime / 1000) % 2 == 0) {
        parent.textSize(48);
        parent.fill(255);
        parent.text("PAUSE", LEFT_OFFSET + 50, 70);
        parent.stroke(255);
        parent.rect(LEFT_OFFSET + 215, 37, 8, 28);
        parent.rect(LEFT_OFFSET + 232, 37, 8, 28);
      }
    }

    parent.textSize(100);
    int x = rand.nextInt(2);
    int y = rand.nextInt(2);
    parent.text("PLEASE STEP FORWARD", 387 + x, 520 + y);
  }

  private int intoSeconds(long millis) {
    return floor(millis/1000);
  }

}
