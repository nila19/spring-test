package com.hello.config.props;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "app.hello")
public class HelloProps {

  /**
   * Special message to convey to the world.
   */
  @NotBlank
  private String message;

  /**
   * Message sender email Id.
   */
  @NotBlank
  @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
  private String fromId;
}
