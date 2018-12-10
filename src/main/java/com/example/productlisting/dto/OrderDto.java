package com.example.productlisting.dto;


import com.example.productlisting.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String email;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = DateUtils.DATE_FORMAT,
            timezone = DateUtils.DATE_TIMEZONE
    )
    private Date createdAt;
    private List<ProductDto> products;
}
