package App.dao;

import App.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    Optional<Event> findByName(String name);
}
