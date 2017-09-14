package music.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ferenc_Buzas on 13 Sep 2017
 */
public class Composer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Composer.class);

    public long id;
    public String name;
    public int birthYear;

    public Composer() {
        LOGGER.debug("Composer()");

        id = 0;
        name = null;
        birthYear = 0;
    }

    public Composer(long id, String name, int birthYear) {
        LOGGER.debug("Composer() id={}, title={}", id, name);

        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String toString() {

        return "Composer[id=" +id+ ", title=" +name+ " birthYear=" + birthYear +"]";
    }
}
