package org.toby.kinectvideomask;

import org.toby.kinectvideomask.base.BaseLoader;
import org.toby.kinectvideomask.features.FeatureLoader;
import org.toby.kinectvideomask.bugs.BugLoader;
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
  private BugLoader bug;
  private boolean currentlyBugging = false;

  private TextOverlay textOverlay;
  private KinectPV2 kinect;
  private long timeBegin;
  private long timeOfLastFeature;

  private boolean someoneHere = false;
  private long timeOfLastSeen;
  private long timeSinceLastSeen;

  private PImage savedBackground;
  private Random rand;
  private PImage outputVideo;
  private SoundFile softFuzz;
  private long someoneFoundTime;
  private boolean loading = false;
  private boolean loaded;

  public static void main(String[] args) {
    PApplet.main("org.toby.kinectvideomask.VideoMaskDegree");
  }

  public void settings() {
    fullScreen();
  }

  public void setup() {
    String background = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png";
    String vhsFont = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vcr.ttf";

    savedBackground = loadImage(background);
    savedBackground.resize(MAIN_WIDTH, MAIN_HEIGHT);
    rand = new Random();
    base = new BaseLoader();
    feature = new FeatureLoader(this);
    bug = new BugLoader(this);
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
    bodyCounter(bodyList);

    boolean shouldBug = rand.nextInt(250) == 0;
    long timeSinceLastFeature = System.currentTimeMillis() - timeOfLastFeature;
    boolean toFeature = (timeSinceLastFeature > 18000 && rand.nextInt(250) == 0) || timeSinceLastFeature > 25000;

    if (timeSinceLastSeen > 5000 || loading) {
      stopFuzz();
      image(new PImage(MAIN_WIDTH, MAIN_HEIGHT), LEFT_DISPLAY_OFFSET, 0);
      if (loading) {
        textOverlay.pauseScreen(currentTime, true);
      } else {
        textOverlay.pauseScreen(currentTime, false);
      }
    } else if (loaded) {
      image(bug.executeDownloadedStatic(outputVideo, body, savedBackground, kinect), LEFT_DISPLAY_OFFSET, 0);
    } else {
      startFuzz();
      if (toFeature || currentlyFeaturing || shouldBug || currentlyBugging) {
        if (toFeature || currentlyFeaturing) {
          //featuring
          outputVideo = feature.execute(liveVideo, body, savedBackground, kinect);
          currentlyFeaturing = feature.isCurrentlyFeaturing();
          timeOfLastFeature = System.currentTimeMillis();
        } else {
          outputVideo = liveVideo;
        }
        if (shouldBug || currentlyBugging) {
          //bugging
          outputVideo = bug.execute(outputVideo, body, savedBackground, kinect);
          currentlyBugging = bug.isCurrentlyBugging();
        }
      } else {
        //basing
        outputVideo = base.executeBase(liveVideo, body, savedBackground, kinect);
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
    String softFuzzSound = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/audio/vhs.wav";
    softFuzz = new SoundFile(this, softFuzzSound);
    softFuzz.loop();
    softFuzz.amp(0.2f); //volume
 }

  private void startFuzz() {
    if (!softFuzz.isPlaying()) {
      softFuzz.loop();
    }
  }

  private void stopFuzz() {
    if (softFuzz.isPlaying()) {
      softFuzz.stop();
    }
  }

  private void setUpKinect(VideoMaskDegree videoMaskDegree) {
    kinect = new KinectPV2(videoMaskDegree);
    kinect.enableColorImg(true);
    kinect.enableBodyTrackImg(true);
    kinect.enableDepthImg(true);
    kinect.enableInfraredImg(true);
    kinect.init();
  }

  private void bodyCounter(ArrayList<PImage> bodyList) {
    if (bodyList.size() > 0 && (System.currentTimeMillis() - someoneFoundTime) < 1500) {
      //people tracking < 1 second
      loading = true;
      timeSinceLastSeen = 0;
      someoneHere = true;
    } else if (bodyList.size() > 0 && (System.currentTimeMillis() - someoneFoundTime) < 1700) {
      loaded = true;
      loading = false;
      timeSinceLastSeen = 0;
      someoneHere = true;
    } else if (bodyList.size() > 0) {
      //people tracking > 1.7 second
      loading = false;
      loaded = false;
    } else if (someoneHere) {
      loaded = false;
      //just lost people
      timeOfLastSeen = System.currentTimeMillis();
      timeSinceLastSeen = 0;
      someoneHere = false;
    } else {
      loaded = false;
      //no people
      timeSinceLastSeen = System.currentTimeMillis() - timeOfLastSeen;
      someoneFoundTime = System.currentTimeMillis();
    }
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
      savedBackground = loadImage(background);
    }
  }
}