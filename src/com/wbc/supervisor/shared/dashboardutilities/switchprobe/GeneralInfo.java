package com.wbc.supervisor.shared.dashboardutilities.switchprobe;

public interface GeneralInfo
{



    long getFilesize();

    void setFilesize( long filesize );

    boolean isComplete();

    void setComplete( boolean complete );

    boolean isHasInterfaces();

    void setHasInterfaces( boolean hasInterfaces );

    boolean isHasMacTable();

    void setHasMacTable( boolean hasMacTable );

    String getProbeVersion();

    void setProbeVersion( String probeVersion );

    String getSwitchIp();

    void setSwitchIp( String switchIp );

    String getCommunityUsed();

    void setCommunityUsed( String communityUsed );

    String getSystemName();

    void setSystemName( String systemName );

    String getLocation();

    void setLocation( String location );

    String getDescription();

    void setDescription( String description );

    String getDefaultGateway();

    void setDefaultGateway( String defaultGateway );

    int getNumIPs();

    void setNumIPs( int numIPs );

    int getNumIfs();

    void setNumIfs( int numIfs );

    int getNumPorts();

    void setNumPorts( int numPorts );

    int getNumVLANs();

    void setNumVLANs( int numVLANs );

    int getNumArps();

    void setNumArps( int numArps );

    int getNumPortForMac();

    void setNumPortForMac( int numPortForMac );
}
