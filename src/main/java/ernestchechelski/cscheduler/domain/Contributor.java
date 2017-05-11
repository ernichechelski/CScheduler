package ernestchechelski.cscheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Contributor.
 */
@Entity
@Table(name = "contributor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contributor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 5, max = 32)
    @Column(name = "jhi_hash", length = 32, nullable = false)
    private String hash;

    @ManyToOne
    private User owner;

    @ManyToMany(mappedBy = "contributors")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Event> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Contributor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public Contributor hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public User getOwner() {
        return owner;
    }

    public Contributor owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Contributor events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Contributor addEvents(Event event) {
        this.events.add(event);
        event.getContributors().add(this);
        return this;
    }

    public Contributor removeEvents(Event event) {
        this.events.remove(event);
        event.getContributors().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contributor contributor = (Contributor) o;
        if (contributor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contributor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contributor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hash='" + getHash() + "'" +
            "}";
    }
}
