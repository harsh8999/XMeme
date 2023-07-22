package com.crio.starter.exchange;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemeRequestDto {

  @NotEmpty(message = "Name cannot be null")
  String name;

  @NotEmpty(message = "Caption cannot be null")
  String caption;

  @NotEmpty(message = "URL cannot be null")
  String url;
  
}
