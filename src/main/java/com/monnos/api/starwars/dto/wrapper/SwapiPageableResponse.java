package com.monnos.api.starwars.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class SwapiPageableResponse<T> extends PageImpl<T> {

    private String next;
    private String previous;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SwapiPageableResponse(@JsonProperty("results") List<T> content,
                                 @JsonProperty("next") String next,
                                 @JsonProperty("previous") String previous,
                                 @JsonProperty("count") long total) {
        super(content, PageRequest.of(1,10), total);
        this.previous = previous;
        this.next = next;
    }

    public SwapiPageableResponse(List<T> content) {
        super(content);
    }

    public SwapiPageableResponse() {
        super(new ArrayList<T>());
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }
}
