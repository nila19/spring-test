package com.springtest.spring.config.props;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "app.datasource")
public class DatasourceProps {

  /**
   * URL of the database to connect to.
   */
  @NotBlank
  private String url;

  /**
   * User ID for connecting to the database.
   */
  @NotBlank
  @Length(max = 16, min = 2)
  private String username;

  /**
   * Password for connecting to the database.
   */
  @NotBlank
  private String password;

  /**
   * Database driver class name.
   */
  @NotBlank
  private String driverClassName;

  /**
   * Dialect to be used for querying the database.
   */
  @NotBlank
  private String dialect;
}
