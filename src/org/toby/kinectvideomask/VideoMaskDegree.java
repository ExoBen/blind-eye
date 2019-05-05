package org.toby.kinectvideomask;

import org.toby.kinectvideomask.base.BaseLoader;
import org.toby.kinectvideomask.features.FeatureLoader;
import org.toby.kinectvideomask.glitches.GlitchLoader;
import processing.core.*;
import processing.sound.*;
import KinectPV2.*;

import java.util.ArrayList;
import java.util.Random;

import static org.toby.kinectvideomask.UtilitiesAndConstants.*;

public class VideoMaskDegree extends PApplet {

  private BaseLoader base;
  private FeatureLoader feature;
  private boolean currentlyFeaturing = false;
  private GlitchLoader glitch;
  private boolean currentlyGlitching = false;

  private TextOverlay textOverlay;
  private KinectPV2 kinect;
  private long timeBegin;
  private long timeOfLastFeature;

  private boolean someoneHere = false;
  private long timeOfLastSeen;
  private long timeSinceLastSeen;
  private int darkness;

  private SoundFile clickBuzz;
  private PImage staticBackground;
  private Random rand;
  private PImage outputVideo;

  public static void main(String[] args) {
    PApplet.main("org.toby.kinectvideomask.VideoMaskDegree");
  }

  public void settings() {
    fullScreen();
  }

  public void setup() {
    String background = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png";
    String vhsFont = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vcr.ttf";

    staticBackground = loadImage(background);
    staticBackground.resize(MAIN_WIDTH, MAIN_HEIGHT);
    rand = new Random();
    base = new BaseLoader();
    feature = new FeatureLoader();
    glitch = new GlitchLoader();
    textOverlay = new TextOverlay(this);
    timeBegin = System.currentTimeMillis();
    timeOfLastFeature = timeBegin;
    setUpKinect(this);
    setUpSounds();
    textFont(createFont(vhsFont, 48));
  }

  @SuppressWarnings("unchecked")
  public void draw() {
    background(0);
    noTint();
    long currentTime = System.currentTimeMillis() - timeBegin;
    PImage cropBody = kinect.getBodyTrackImage().get(39, 32, KINECT_WIDTH, KINECT_HEIGHT);
    PImage body = Upscaler.upscaler(cropBody, KINECT_WIDTH*KINECT_HEIGHT);
    body.filter(THRESHOLD);
    PImage liveVideo = kinect.getColorImage().get(LEFT_OFFSET, 0, MAIN_WIDTH, MAIN_HEIGHT);
    liveVideo.filter(GRAY);

    ArrayList<PImage> bodyList = kinect.getBodyTrackUser();
    if (bodyList.size() > 0) {
      timeSinceLastSeen = 0;
      someoneHere = true;
    } else if (someoneHere) {
      timeOfLastSeen = System.currentTimeMillis();
      timeSinceLastSeen = 0;
      someoneHere = false;
      darkness = 0;
    } else {
      timeSinceLastSeen = System.currentTimeMillis() - timeOfLastSeen;
    }

    int random = rand.nextInt(200);
    long timeSince = System.currentTimeMillis() - timeOfLastFeature;
    boolean toFeature = (timeSince > 8000 && rand.nextInt(200) == 0) || timeSince > 10000;

    if (timeSinceLastSeen > 10000) {
      image(new PImage(MAIN_WIDTH, MAIN_HEIGHT), LEFT_DISPLAY_OFFSET, 0);
      textOverlay.pauseScreen(currentTime);
    } else {
      if (toFeature || currentlyFeaturing || random == 0 || currentlyGlitching) {
        if (toFeature || currentlyFeaturing) {
          //featuring
          outputVideo = feature.executeFeature(liveVideo, body, staticBackground, kinect);
          currentlyFeaturing = feature.isCurrentlyFeaturing();
          if (!clickBuzz.isPlaying()) {
            clickBuzz.play();
          }
          timeOfLastFeature = System.currentTimeMillis();
        } else {
          outputVideo = liveVideo;
        }
        if (random == 0 || currentlyGlitching) {
          //glitching
          outputVideo = glitch.executeGlitch(outputVideo, body, kinect);
          currentlyGlitching = glitch.isCurrentlyGlitching();
        }
      } else {
        //basing
        outputVideo = base.executeBase(liveVideo, body, staticBackground, bodyList);
      }
      if (timeSinceLastSeen > 4500) {
        float op = floor(254-(((timeSinceLastSeen - 4500f)/500f)*255f));
        tint(128, op);
      }
      image(outputVideo, LEFT_DISPLAY_OFFSET, 0);
      textOverlay.info(currentTime, kinect);
    }
  }

  // ---------------------------------------------------------------------

  private void setUpSounds() {
    String softFuzzSound = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vhs.wav";
    String clickBuzzSound = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/clickBuzz.wav";
    SoundFile softFuzz = new SoundFile(this, softFuzzSound);
    softFuzz.loop();
    softFuzz.amp(0.2f); //volume
    clickBuzz = new SoundFile(this, clickBuzzSound);
    clickBuzz.amp(0.4f);
 }

  private void setUpKinect(VideoMaskDegree videoMaskDegree) {
    kinect = new KinectPV2(videoMaskDegree);
    kinect.enableColorImg(true);
    kinect.enableBodyTrackImg(true);
    kinect.enableDepthImg(true);
    kinect.enableInfraredImg(true);
    kinect.init();
  }

  public void mousePressed() {
    if (mouseButton == RIGHT) {
      exit();
    }
  }

  public void keyPressed() {
    if (key == 32) {
      outputVideo.save("F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png");
      String background = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png";
      staticBackground = loadImage(background);
      System.out.println("space");
    }
  }
}