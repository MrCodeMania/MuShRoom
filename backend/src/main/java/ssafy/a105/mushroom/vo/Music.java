package ssafy.a105.mushroom.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
public class Music {

  private String url;
  private String fileName;
  private Integer volume = 0;
  private Integer distortion = 0;
  private Integer gain = 0;
  @CreatedDate
  private Date timestamp;

  @Builder
  public Music(String url, String fileName, Integer volume, Integer distortion, Integer gain) {
    this.url = url;
    this.fileName = fileName;
    this.volume = volume;
    this.distortion = distortion;
    this.gain = gain;
  }
}
