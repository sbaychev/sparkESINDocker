package data;

import java.io.Serializable;
import java.util.Objects;

public final class Episodes implements Serializable {

    private static final long serialVersionUID = 7993796581494284298L;

    /**
     * episode title
     */
    public String title;

    /**
     * initial date
     */
    public String air_date;

    /**
     * main actor playing the Doctor in episode
     */
    public int doctor;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public Episodes() { }

    /**
     * All-args constructor.
     *
     * @param title episode title
     * @param air_date initial date
     * @param doctor main actor playing the Doctor in episode
     */
    public Episodes(String title, String air_date, Integer doctor) {
        this.title = title;
        this.air_date = air_date;
        this.doctor = doctor;
    }

    public String getTitle() {
        return title;
    }

    public Episodes setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAir_date() {
        return air_date;
    }

    public Episodes setAir_date(String air_date) {
        this.air_date = air_date;
        return this;
    }

    public int getDoctor() {
        return doctor;
    }

    public Episodes setDoctor(int doctor) {
        this.doctor = doctor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Episodes)) {
            return false;
        }
        Episodes episodes = (Episodes) o;
        return getDoctor() == episodes.getDoctor() &&
            Objects.equals(getTitle(), episodes.getTitle()) &&
            Objects.equals(getAir_date(), episodes.getAir_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAir_date(), getDoctor());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Episodes{");
        sb.append("title='").append(title).append('\'');
        sb.append(", air_date='").append(air_date).append('\'');
        sb.append(", doctor=").append(doctor);
        sb.append('}');
        return sb.toString();
    }
}
