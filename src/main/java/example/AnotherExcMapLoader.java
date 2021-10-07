package example;

import com.hazelcast.map.MapLoader;
import org.springframework.dao.DataAccessResourceFailureException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class AnotherExcMapLoader implements MapLoader<Integer, Long> {
    @Override
    public Long load(Integer key) {
        throw new DataAccessResourceFailureException("String", new SQLException());
    }

    @Override
    public Map<Integer, Long> loadAll(Collection<Integer> keys) {
        return null;
    }

    @Override
    public Iterable<Integer> loadAllKeys() {
        return null;
    }
}
