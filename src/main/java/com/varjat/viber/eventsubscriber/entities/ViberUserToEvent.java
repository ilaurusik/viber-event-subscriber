package com.varjat.viber.eventsubscriber.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
public class ViberUserToEvent {

    @Embeddable
    public static class ViberUserToEventId implements Serializable {

        @Column
        private Long viberUserId;

        @Column
        private Long eventId;

        private ViberUserToEventId() {}

        public ViberUserToEventId(
                Long viberUserId,
                Long eventId) {
            this.viberUserId = viberUserId;
            this.eventId = eventId;
        }

        public Long getViberUserId() {
            return viberUserId;
        }

        public Long getEventId() {
            return eventId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ViberUserToEventId that = (ViberUserToEventId) o;
            return Objects.equals(viberUserId, that.viberUserId) &&
                    Objects.equals(eventId, that.eventId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(viberUserId, eventId);
        }
    }

    @EmbeddedId
    public ViberUserToEventId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("viberUserId")
    public ViberUser viberUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    public Event event;

    @Column
    public ZonedDateTime createdOn = ZonedDateTime.now();

    @Column(nullable = false)
    public String answer;

    @Column(nullable = false)
    public byte count = 1;

    private ViberUserToEvent() {}

    public ViberUserToEvent(ViberUser user, Event event) {
        this.viberUser = user;
        this.event = event;
        this.id = new ViberUserToEventId(user.viberUserId, event.eventId);
    }


}
