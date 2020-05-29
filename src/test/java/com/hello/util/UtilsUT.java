package com.hello.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hello.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class UtilsUT {

  @Test
  public void new_whenNotFound() {
    assertThrows(Exception.class, Utils::new);
  }
}
