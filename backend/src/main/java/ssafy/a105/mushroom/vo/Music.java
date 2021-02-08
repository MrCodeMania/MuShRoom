package ssafy.a105.mushroom.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Music {

  private String url;
  private String fileName;
  private Integer volume = 0;
  private Integer distortion = 0;
  private Integer gain = 0;
  private Date timestamp;
}
