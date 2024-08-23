package org.example.eventspotlightback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private Description description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "events_photos",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id", unique = true)
    )
        private Set<Photo> photos = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "events_categories",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
    private LocalDateTime startTime;
    private BigDecimal price;
    @Column(name = "is_online")
    private boolean isOnline;
    @Column(name = "is_top")
    private boolean isTop;
    @Column(name = "is_accepted")
    private boolean isAccepted;
    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private Set<Favorite> favorites = new HashSet<>();
}
