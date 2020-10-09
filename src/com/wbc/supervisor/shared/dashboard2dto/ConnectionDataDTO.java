package com.wbc.supervisor.shared.dashboard2dto;

import java.io.Serializable;

/**
 * Created by JIM on 10/25/2014.
 */
public class ConnectionDataDTO implements Serializable {
    int verifiedConnected;
    int unverifiedConnected;
    int verifiedDisconnected;
    int unverifiedDisconnected;

    public ConnectionDataDTO(int verifiedConnected, int unverifiedConnected, int verifiedDisconnected, int unverifiedDisconnected) {
        this.verifiedConnected = verifiedConnected;
        this.unverifiedConnected = unverifiedConnected;
        this.verifiedDisconnected = verifiedDisconnected;
        this.unverifiedDisconnected = unverifiedDisconnected;
    }

    public ConnectionDataDTO() {
        this.verifiedConnected = 0;
        this.unverifiedConnected = 0;
        this.verifiedDisconnected = 0;
        this.unverifiedDisconnected = 0;
    }

    public int getVerifiedConnected() {
        return verifiedConnected;
    }

    public void setVerifiedConnected(int verifiedConnected) {
        this.verifiedConnected = verifiedConnected;
    }

    public int getUnverifiedConnected() {
        return unverifiedConnected;
    }

    public void setUnverifiedConnected(int unverifiedConnected) {
        this.unverifiedConnected = unverifiedConnected;
    }

    public int getVerifiedDisconnected() {
        return verifiedDisconnected;
    }

    public void setVerifiedDisconnected(int verifiedDisconnected) {
        this.verifiedDisconnected = verifiedDisconnected;
    }

    public int getUnverifiedDisconnected() {
        return unverifiedDisconnected;
    }

    public void setUnverifiedDisconnected(int unverifiedDisconnected) {
        this.unverifiedDisconnected = unverifiedDisconnected;
    }

    public void incrementVerifiedConnected() {
        verifiedConnected++;
    }
    public void incrementVerifiedDisconnected() {
        verifiedDisconnected++;
    }

    public void incrementUnverifiedConnected() {
        unverifiedConnected++;
    }
    public void incrementUnverifiedDisconnected() {
        unverifiedDisconnected++;
    }

    @Override
    public String toString() {
        return "ConnectionDataDTO{" +
                " VC=" + verifiedConnected +
                ", UC=" + unverifiedConnected +
                ", VD=" + verifiedDisconnected +
                ", UD=" + unverifiedDisconnected +
                '}';
    }
}
