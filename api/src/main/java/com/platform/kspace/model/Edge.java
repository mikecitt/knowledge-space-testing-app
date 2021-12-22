package com.platform.kspace.model;

import javax.persistence.*;
import java.io.Serializable;

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
}

@Embeddable
class EdgeKey implements Serializable {

    @Column(name = "from_id")
    Integer fromId;

    @Column(name = "to_id")
    Integer toId;


    public Integer getFromId() {
        return this.fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return this.toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

}