package ernestchechelski.cscheduler.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "jhi_start")
    private ZonedDateTime start;

    @Column(name = "jhi_stop")
    private ZonedDateTime stop;

    @ManyToOne(optional = false)
    @NotNull
    private Contributor owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_contributors",
               joinColumns = @JoinColumn(name="events_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="contributors_id", referencedColumnName="id"))
    private Set<Contributor> contributors = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Plan plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public Event start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getStop() {
        return stop;
    }

    public Event stop(ZonedDateTime stop) {
        this.stop = stop;
        return this;
    }

    public void setStop(ZonedDateTime stop) {
        this.stop = stop;
    }

    public Contributor getOwner() {
        return owner;
    }

    public Event owner(Contributor contributor) {
        this.owner = contributor;
        return this;
    }

    public void setOwner(Contributor contributor) {
        this.owner = contributor;
    }

    public Set<Contributor> getContributors() {
        return contributors;
    }

    public Event contributors(Set<Contributor> contributors) {
        this.contributors = contributors;
        return this;
    }

    public Event addContributors(Contributor contributor) {
        this.contributors.add(contributor);
        contributor.getEvents().add(this);
        return this;
    }

    public Event removeContributors(Contributor contributor) {
        this.contributors.remove(contributor);
        contributor.getEvents().remove(this);
        return this;
    }

    public void setContributors(Set<Contributor> contributors) {
        this.contributors = contributors;
    }

    public Plan getPlan() {
        return plan;
    }

    public Event plan(Plan plan) {
        this.plan = plan;
        return this;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", start='" + getStart() + "'" +
            ", stop='" + getStop() + "'" +
            "}";
    }
}
