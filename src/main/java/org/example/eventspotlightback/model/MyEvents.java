package org.example.eventspotlightback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"id", "events"})
@Table(name = "my_events")
@Accessors(chain = true)
public class MyEvents {
    @Id
    private Long id;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "id")
    private User user;
    @ManyToMany(mappedBy = "myEvents", fetch = FetchType.LAZY)
    private Set<Event> events;
}
