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
    private Item from;

    @ManyToOne
    @MapsId("toId")
    @JoinColumn(name = "to_id")
    private Item to;

    @ManyToOne
    private KnowledgeSpace knowledgeSpace;
}

@Embeddable
class EdgeKey implements Serializable {

    @Column(name = "from_id")
    Integer fromId;

    @Column(name = "to_id")
    Integer toId;
}