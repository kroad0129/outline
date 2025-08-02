package com.outline.outline.post.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryResult {
    private String summarizedTitle;
    private String summarizedContent;
}
