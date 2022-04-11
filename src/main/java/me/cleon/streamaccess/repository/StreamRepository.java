package me.cleon.streamaccess.repository;

import me.cleon.streamaccess.domain.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Stream findByStreamCode(String streamCode);
}
