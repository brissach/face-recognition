package gg.clouke.facerec;

/**
 * @author Clouke
 * @since 16.02.2024 12:32
 * © face-recognition - All Rights Reserved
 */
public class SampleSize {

  private static int width = -1;
  private static int height = -1;

  public static void width(int w) {
    width = w;
  }

  public static void height(int h) {
    height = h;
  }

  public static void size(int s) {
    width = s;
    height = s;
  }

  public static boolean initiated() {
    return width != -1 || height != -1;
  }

  public static int width() {
    return width;
  }

  public static int height() {
    return height;
  }

  public static int size() {
    return width * height;
  }

}
