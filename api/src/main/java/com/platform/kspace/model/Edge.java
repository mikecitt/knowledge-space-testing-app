package com.platform.kspace.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Edge {

    @EmbeddedId
    private EdgeKey id;

    @ManyToOne
    @MapsId("fromId")
    @JoinColumn(name = "from_id")
    private DomainProblem from;

    @ManyToOne
    @MapsId("toId")
    @JoinColumn(name = "to_id")
    private DomainProblem to;

    @ManyToOne
    @MapsId("knowledgeSpaceId")
    private KnowledgeSpace knowledgeSpace;

    public Edge() {
        
    }

    public Edge(DomainProblem from, DomainProblem to) {
        this.from = from;
        this.to = to;
        this.id = new EdgeKey();
    }

    public void setKnowledgeSpace(KnowledgeSpace knowledgeSpace) {
        this.knowledgeSpace = knowledgeSpace;
    }


    public EdgeKey getId() {
        return this.id;
    }

    public void setId(EdgeKey id) {
        this.id = id;
    }

    public DomainProblem getFrom() {
        return this.from;
    }

    public void setFrom(DomainProblem from) {
        this.from = from;
    }

    public DomainProblem getTo() {
        return this.to;
    }

    public void setTo(DomainProblem to) {
        this.to = to;
    }

    public KnowledgeSpace getKnowledgeSpace() {
        return this.knowledgeSpace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}

@Embeddable
class EdgeKey implements Serializable {

    @Column(name = "from_id")
    UUID fromId;

    @Column(name = "to_id")
    UUID toId;

    @Column(name = "knowledgeSpace_id")
    Integer knowledgeSpaceId;

    public UUID getFromId() {
        return this.fromId;
    }

    public void setFromId(UUID fromId) {
        this.fromId = fromId;
    }

    public UUID getToId() {
        return this.toId;
    }

    public void setToId(UUID toId) {
        this.toId = toId;
    }

}