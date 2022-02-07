package com.platform.kspace.dto;

import java.util.*;

public class KnowledgeSpaceComparisonDTO {
    public Map<UUID, Node> nodes;
    public Map<CustomKey, Edge> edges;

    public KnowledgeSpaceComparisonDTO() {
        this.nodes = new HashMap<UUID, Node>();
        this.edges = new HashMap<CustomKey, Edge>();
    }

    public void addNode(UUID domainProblemId, String domainProblemText, SpaceType spaceType) {
        SpaceType temp = null;
        if(this.nodes.get(domainProblemId) == null)
            temp = spaceType;
        else if(this.nodes.get(domainProblemId).spaceType == spaceType)
            return;
        else
            temp = SpaceType.BOTH;
        this.nodes.put(domainProblemId, new Node(domainProblemId, domainProblemText, temp));
    }

    public void addEdge(UUID from, UUID to, SpaceType spaceType) {
        SpaceType temp = null;
        if(this.edges.get(new CustomKey(from, to)) == null)
            temp = spaceType;
        else if(this.edges.get(new CustomKey(from, to)).spaceType == spaceType)
            return;
        else
            temp = SpaceType.BOTH;
        this.edges.put(new CustomKey(from, to), new Edge(from, to, temp));
    }

    public Collection<Edge> getEdges() {
        return edges.values();
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }
}

class Node {
    public UUID id;
    public String label;
    public SpaceType spaceType;

    public Node(UUID id, String label, SpaceType spaceType) {
        this.id = id;
        this.label = label;
        this.spaceType = spaceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }
}

class Edge {
    public UUID source;
    public UUID target;
    public SpaceType spaceType;

    public Edge(UUID source, UUID target, SpaceType spaceType) {
        this.source = source;
        this.target = target;
        this.spaceType = spaceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return source.equals(edge.source) && target.equals(edge.target);
    }


}

class CustomKey {

    private UUID x;
    private UUID y;

    public CustomKey(UUID x, UUID y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomKey customKey = (CustomKey) o;
        return x.equals(customKey.x) && y.equals(customKey.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}