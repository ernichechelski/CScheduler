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
 * A Plan.
 */
@Entity
@Table(name = "plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "plan")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Event> events = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Plan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Plan events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Plan addEvents(Event event) {
        this.events.add(event);
        event.setPlan(this);
        return this;
    }

    public Plan removeEvents(Event event) {
        this.events.remove(event);
        event.setPlan(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public User getOwner() {
        return owner;
    }

    public Plan owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plan plan = (Plan) o;
        if (plan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Plan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
