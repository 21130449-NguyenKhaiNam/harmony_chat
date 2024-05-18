package com.example.harmony_chat.model;

public class Hierarchy {
    private long  id;

    private Room room;

    private User leader;
    private User deputy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public User getDeputy() {
        return deputy;
    }

    public void setDeputy(User deputy) {
        this.deputy = deputy;
    }
}
