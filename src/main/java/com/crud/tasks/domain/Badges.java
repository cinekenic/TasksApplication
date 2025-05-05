package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Badges {
    @JsonProperty("votes")
    private int votes;
    @JsonProperty("attachmentsByType")
    private AttachmentsByType attachmentsByType;
}
