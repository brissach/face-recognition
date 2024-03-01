package gg.clouke.facerec;

import gg.acai.acava.scheduler.AsyncPlaceholder;
import gg.acai.acava.scheduler.Schedulers;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Clouke
 * @since 01.03.2024 20:44
 * © face-recognition - All Rights Reserved
 */
public class DataSetLoader {

  public static Builder builder() {
    return new Builder();
  }

  private final List<BufferedImage> images = new ArrayList<>();

  private final String url;
  private final String loadFromFilePath;
  private final String exportPath;
  private final long sleep;
  private final int fetchAmount;
  private final int resizeX;
  private final int resizeY;

  DataSetLoader(Builder builder) {
    url = builder.url;
    loadFromFilePath = builder.loadFromFilePath;
    exportPath = builder.exportPath;
    sleep = builder.sleep;
    fetchAmount = builder.fetchAmount;
    resizeX = builder.resizeX;
    resizeY = builder.resizeY;
  }

  @Nonnull
  public DataSet load(double splitRatio) {
    if (url != null) {
      for (int i = 0; i < fetchAmount; i++) {
        try {
          BufferedImage image = resize(fetchImage(url));
          if (exportPath != null) {
            String name = UUID.randomUUID() + ".jpg";
            ImageIO.write(image, "jpg", new File(exportPath, name));
          }
          images.add(image);
          System.out.println("Added image " + i);
          if (sleep > 0L)
            Thread.sleep(sleep);
        } catch (Exception e) {
          e.printStackTrace();
          break;
        }
      }
    }

    if (loadFromFilePath != null) {
      File directory = new File(loadFromFilePath);
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          try {
            BufferedImage image = resize(ImageIO.read(file));
            images.add(image);
            System.out.println("Added image " + file.getName());
          } catch (IOException e) {
            e.printStackTrace();
            break;
          }
        }
      }
    }

    System.out.println("Size: " + images.size());
    return new DataSet(images, splitRatio);
  }

  BufferedImage resize(BufferedImage image) {
    if (resizeX > 0 || resizeY > 0) {
      int x = resizeX <= 0 ? image.getWidth() : resizeX;
      int y = resizeY <= 0 ? image.getHeight() : resizeY;
      image = ImageRescale.scaleImage(image, x, y);
    }
    return image;
  }

  @Nonnull
  public AsyncPlaceholder<DataSet> loadAsync(double splitRatio) {
    return Schedulers.supplyAsync(() -> load(splitRatio));
  }

  static BufferedImage fetchImage(String imageUrl)
    throws IOException {
      URL url = new URL(imageUrl);
      return ImageIO.read(url);
    }

  public static class Builder {

    private String url = "https://thispersondoesnotexist.com";
    private String loadFromFilePath;
    private String exportPath;
    private long sleep = 1200L;
    private int fetchAmount = 100;
    private int resizeX = SampleSize.width();
    private int resizeY = SampleSize.height();

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder loadFromFilePath(String path) {
      loadFromFilePath = path;
      return this;
    }

    public Builder exportPath(String path) {
      exportPath = path;
      return this;
    }

    public Builder sleep(long sleep) {
      this.sleep = sleep;
      return this;
    }

    public Builder fetchAmount(int amount) {
      fetchAmount = amount;
      return this;
    }

    public Builder resizeX(int x) {
      resizeX = x;
      return this;
    }

    public Builder resizeY(int y) {
      resizeY = y;
      return this;
    }

    public DataSetLoader build() {
      return new DataSetLoader(this);
    }

  }

}
