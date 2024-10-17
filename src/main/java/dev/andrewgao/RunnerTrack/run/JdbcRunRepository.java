package dev.andrewgao.RunnerTrack.run;

import java.util.List;


import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Optional;

@Repository
public class JdbcRunRepository implements RunRepository{

    private final JdbcClient jdbcClient;

    public JdbcRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("Select * From run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("Select * From Run where id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var update = jdbcClient.sql("Insert into Run (id, title, start_time, complete_time, miles, location) values (?, ?, ?, ?, ?, ?)")
                        .params(List.of(run.id(), run.title(), run.startTime(), run.completeTime(), run.miles(), run.location().toString()))
                        .update();

        Assert.state(update == 1, "Failed to create run: " + run.title());
    }

    public void update(Run run, Integer id) {
        var update = jdbcClient.sql("Update Run set title = ?, start_time = ?, complete_time = ?, miles = ?, location = ? where id = ?")
                        .params(List.of(run.title(), run.startTime(), run.completeTime(), run.miles(), run.location().toString(), id))
                        .update();

        Assert.state(update == 1, "Failed to update run: " + id);
    }

    public void delete(Integer id) {
        var update = jdbcClient.sql("Delete from Run where id = :id")
                        .param("id", id)
                        .update();

        Assert.state(update == 1, "Failed to delete run: " + id);
    }

    public int count() {
        return jdbcClient.sql("Select * from Run")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Run> runs) {
        runs.stream().forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return  jdbcClient.sql("Select * from Run where location = :location")
                    .param("location", location)
                    .query(Run.class)
                    .list();
    }


}
