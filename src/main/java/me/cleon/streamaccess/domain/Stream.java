package me.cleon.streamaccess.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "streams")
public class Stream {
    @Id
    @Column(name="stream_id")
    @SequenceGenerator(name = "ID", sequenceName = "STREAM_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID")
    Long streamId;

    @Column(name = "stream_code")
    String streamCode;

    @Column(name = "stream_key")
    String streamKey;

    @Column(name = "created_at")
    Date creationDate;

    @Column(name = "stream_owner_id")
    Integer streamOwnerId;

    @Column(name="stream_title")
    String streamTitle;

    @Column(name = "stream_description")
    String streamDescription;

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    public String getStreamCode() {
        return streamCode;
    }

    public void setStreamCode(String streamCode) {
        this.streamCode = streamCode;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStreamOwnerId() {
        return streamOwnerId;
    }

    public void setStreamOwnerId(Integer streamOwnerId) {
        this.streamOwnerId = streamOwnerId;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public void setStreamTitle(String streamTitle) {
        this.streamTitle = streamTitle;
    }

    public String getStreamDescription() {
        return streamDescription;
    }

    public void setStreamDescription(String streamDescription) {
        this.streamDescription = streamDescription;
    }
}
