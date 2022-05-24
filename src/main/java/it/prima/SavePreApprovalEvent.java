package it.prima;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "approvedAt",
        "savedOn",
        "id"
})
public class SavePreApprovalEvent {

    public SavePreApprovalEvent() {

    }


    public SavePreApprovalEvent(String approvedAt, String savedOn, String id) {
        super();
        this.id = id;
        this.approvedAt = approvedAt;
        this.savedOn = savedOn;
    }

    @JsonProperty(required = true)
    public String approvedAt;
    @JsonProperty(required = true)
    public String savedOn;
    @JsonProperty(required = true)
    public String id;
}