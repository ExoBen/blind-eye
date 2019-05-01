package org.toby.kinectvideomask;

import processing.core.*;
import processing.sound.*;
import KinectPV2.*;

import java.util.ArrayList;

public class VideoMaskDegree extends PApplet {

  private KinectPV2 kinect;
  TextOverlay textOverlay;
  private int black = -16777216;
  private PImage staticBackground;
  private int timeBegin;
  private int currentTime;
  private PImage[] savedFrame = new PImage[40];
  private int glitchCounter = 0;
  private boolean showThePast = false;
  private PImage outputVideo;
  private SoundFile clickBuzz;

  public static void main(String[] args) {
    PApplet.main("org.toby.kinectvideomask.VideoMaskDegree");
  }

  public void settings() {
    size(1302, 1080);
  }

  public void setup() {
    staticBackground = loadImage("F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.tif");
    staticBackground.resize(1302, 1080);

    kinect = new KinectPV2(this);
    kinect.enableColorImg(true);
    kinect.enableBodyTrackImg(true);
    kinect.init();

    textOverlay = new TextOverlay(this);

    PFont vcr = createFont("F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vcr.ttf", 48);
    textFont(vcr);
    timeBegin = millis();
    SoundFile softFuzz = new SoundFile(this, "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vhs.wav");
    softFuzz.loop();
    softFuzz.amp(0.2f); //volume
    clickBuzz = new SoundFile(this, "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/clickBuzz.wav");
    clickBuzz.amp(0.4f);
  }

  public void draw() {
    background(0);
    currentTime = millis()-timeBegin;
    PImage body = kinect.getBodyTrackImage();
    PImage cropBody = body.get(39, 32, 434, 360);
    int cropBodySize = (cropBody.width * cropBody.height);

    PImage bodyUpscaled = upscaler(cropBody, cropBodySize);
    PImage liveVideo = loadLiveVideo();
    ArrayList<PImage> bodyTrackList = kinect.getBodyTrackUser();

    int magicNumber = (int) random(0, 500);
    if (magicNumber == 0 || magicNumber == 1) {
      outputVideo = twoTone(bodyUpscaled, color(255), color(0));
    } else if (magicNumber == 2 || showThePast) {
      outputVideo = showThePast(liveVideo);
    } else if (currentTime % 10000 > 8000) {
      if (!clickBuzz.isPlaying()) {
        clickBuzz.play();
      }
      outputVideo = twoTone(bodyUpscaled, color(255, 1, 145), color(0, 151, 254));
      //outputVideo = bitmapFuzz(bodyUpscaled, liveVideo);
    } else if (bodyTrackList.size() > 2) {
      PImage upscaledSecondPerson = upscaler(bodyTrackList.get(1), cropBodySize);
      outputVideo = ghosting(upscaledSecondPerson, liveVideo);
    } else {
      outputVideo = ghosting(bodyUpscaled, liveVideo);
    }
    image(outputVideo, 0, 0);

    textOverlay.textOverlay(currentTime, kinect);
  }

  // ---------------------------------------------------------------------

  private PImage upscaler(PImage cropBody, int cropBodySize) {
    PImage bodyUpscaled = new PImage(1302, 1080);
    bodyUpscaled.loadPixels();
    for (int i = 0; i < cropBodySize; i++) {
      bodyUpscaled.pixels[i*3] = cropBody.pixels[i];
      bodyUpscaled.pixels[i*3+1] = cropBody.pixels[i];
      bodyUpscaled.pixels[i*3+2] = cropBody.pixels[i];
    }
    for (int i = cropBody.height-1; i >=0; i--) {
      PImage line = bodyUpscaled.get(0, i, 1302, 1);
      bodyUpscaled.set(0, i*3, line);
      bodyUpscaled.set(0, i*3+1, line);
      bodyUpscaled.set(0, i*3+2, line);
    }
    bodyUpscaled.filter(THRESHOLD);
    return bodyUpscaled;
  }

  private PImage loadLiveVideo() {
    PImage fullLiveVideo = kinect.getColorImage();
    return fullLiveVideo.get(309, 0, 1302, 1080);
  }

  private PImage ghosting(PImage bodyUpscaled, PImage liveVideo) {
    liveVideo.loadPixels();
    int bodyUpscaledSize = (bodyUpscaled.width * bodyUpscaled.height);
    boolean wasWhiteLR = false;
    for (int i = 0; i < bodyUpscaledSize; i++) {
      if (bodyUpscaled.pixels[i] == black) {
        liveVideo.pixels[i] = staticBackground.pixels[i]; // standard ghosting
        if (wasWhiteLR) {
          randomnessHorizontal(i, liveVideo);
        }
        wasWhiteLR = false;
      } else {
        if (!wasWhiteLR) {
          randomnessHorizontal(i, liveVideo);
        }
        wasWhiteLR = true;
      }
      if (bodyUpscaled.pixels[i] == black && floor(i/1302) < 1078 && bodyUpscaled.pixels[i+1304] != black) {
        randomnessVertical(i, liveVideo);
      }
    }
    liveVideo.updatePixels();
    return liveVideo;
  }

  PImage bitmapFuzz(PImage bodyUpscaled, PImage liveVideo) {
    liveVideo.loadPixels();
    int bodyUpscaledSize = (bodyUpscaled.width * bodyUpscaled.height);

    for (int i = 0; i < bodyUpscaledSize; i++) {
      int magicNumber2 = (int) random(0, 4);
      if (bodyUpscaled.pixels[i] == black && magicNumber2 == 1) {
        int magicNumber = (int) random(0, 2);
        liveVideo.pixels[i] = (magicNumber == 1 ? color(0) : color(255));
      }
    }
    liveVideo.updatePixels();
    return liveVideo;
  }

  private void randomnessHorizontal(int i, PImage liveVideo) {
    float rand = random(30, 40); // line length is 30 to 40 pixels
    int randInt = (int) rand;
    for (int j = -randInt/2; j <= randInt; j++) {
      int position = i % 1302 + j;
      if ((position >= 0) && (position < 1302)) {
        liveVideo.pixels[i+j] = staticBackground.pixels[i+j];
      }
    }
  }

  private void randomnessVertical(int i, PImage liveVideo) {
    float rand = random(50, 70);
    int randInt = (int) rand;
    for (int j = 0; j <= randInt; j++) {
      if ((floor(i / 1302) + j >= 0) && (ceil(i/1302) + j < 1080)) {
        liveVideo.pixels[i+(j*1302)] = staticBackground.pixels[i+(j*1302)];
      }
    }
  }

  private PImage twoTone(PImage bodyUpscaled, int foreground, int background) {
    PImage mask = new PImage(bodyUpscaled.width, bodyUpscaled.height);
    int bodyUpscaledSize = (bodyUpscaled.width * bodyUpscaled.height);
    for (int i = 0; i < bodyUpscaledSize; i++) {
      if (bodyUpscaled.pixels[i] == black) {
        mask.pixels[i] = foreground;
      } else {
        mask.pixels[i] = background;
      }
    }
    return mask;
  }

  private PImage showThePast(PImage liveVideo) {
    showThePast = true;
    if (savedFrame[glitchCounter] != null) {
      outputVideo = savedFrame[glitchCounter];
    }
    savedFrame[glitchCounter] = liveVideo;
    glitchCounter++;
    if (glitchCounter >= 40) {
      glitchCounter = 0;
      showThePast = false;
    }
    return outputVideo;
  }

  public void mousePressed() {
    saveFrame();
  }
}