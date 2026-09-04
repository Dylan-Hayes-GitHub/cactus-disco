package com.cactus.disco.dto.outbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ErrorDTO {

    private String errorMessage;
    private Integer errorCode;

}
