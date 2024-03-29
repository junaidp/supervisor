/**
 * This class is generated by jOOQ
 */
package com.wbc.supervisor.database.generated;

/**
 * This class is generated by jOOQ.
 *
 * A class modelling foreign key relationships between tables of the <code>dashboard2</code> 
 * schema
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, Integer> IDENTITY_DMPFILES = Identities0.IDENTITY_DMPFILES;
	public static final org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.PropertiesRecord, Integer> IDENTITY_PROPERTIES = Identities0.IDENTITY_PROPERTIES;
	public static final org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.StatsRecord, Integer> IDENTITY_STATS = Identities0.IDENTITY_STATS;
	public static final org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.ThresholdsRecord, Integer> IDENTITY_THRESHOLDS = Identities0.IDENTITY_THRESHOLDS;
	public static final org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.TopoinfoRecord, Integer> IDENTITY_TOPOINFO = Identities0.IDENTITY_TOPOINFO;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DeviceinfoRecord> KEY_DEVICEINFO_PRIMARY = UniqueKeys0.KEY_DEVICEINFO_PRIMARY;
	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord> KEY_DEVICENAMEINFO_PRIMARY = UniqueKeys0.KEY_DEVICENAMEINFO_PRIMARY;
	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord> KEY_DMPFILES_PRIMARY = UniqueKeys0.KEY_DMPFILES_PRIMARY;
	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.PropertiesRecord> KEY_PROPERTIES_PRIMARY = UniqueKeys0.KEY_PROPERTIES_PRIMARY;
	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.StatsRecord> KEY_STATS_PRIMARY = UniqueKeys0.KEY_STATS_PRIMARY;
	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.ThresholdsRecord> KEY_THRESHOLDS_PRIMARY = UniqueKeys0.KEY_THRESHOLDS_PRIMARY;
	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.TopoinfoRecord> KEY_TOPOINFO_PRIMARY = UniqueKeys0.KEY_TOPOINFO_PRIMARY;
	public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.VendormacsRecord> KEY_VENDORMACS_PRIMARY = UniqueKeys0.KEY_VENDORMACS_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, Integer> IDENTITY_DMPFILES = createIdentity(com.wbc.supervisor.database.generated.tables.Dmpfiles.DMPFILES, com.wbc.supervisor.database.generated.tables.Dmpfiles.DMPFILES.DBID);
		public static org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.PropertiesRecord, Integer> IDENTITY_PROPERTIES = createIdentity(com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES, com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES.PID);
		public static org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.StatsRecord, Integer> IDENTITY_STATS = createIdentity(com.wbc.supervisor.database.generated.tables.Stats.STATS, com.wbc.supervisor.database.generated.tables.Stats.STATS.SID);
		public static org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.ThresholdsRecord, Integer> IDENTITY_THRESHOLDS = createIdentity(com.wbc.supervisor.database.generated.tables.Thresholds.THRESHOLDS, com.wbc.supervisor.database.generated.tables.Thresholds.THRESHOLDS.TID);
		public static org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.TopoinfoRecord, Integer> IDENTITY_TOPOINFO = createIdentity(com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO, com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DeviceinfoRecord> KEY_DEVICEINFO_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Deviceinfo.DEVICEINFO, com.wbc.supervisor.database.generated.tables.Deviceinfo.DEVICEINFO.DEVICEID);
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord> KEY_DEVICENAMEINFO_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Devicenameinfo.DEVICENAMEINFO, com.wbc.supervisor.database.generated.tables.Devicenameinfo.DEVICENAMEINFO.DEVICEID);
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord> KEY_DMPFILES_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Dmpfiles.DMPFILES, com.wbc.supervisor.database.generated.tables.Dmpfiles.DMPFILES.DBID);
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.PropertiesRecord> KEY_PROPERTIES_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES, com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES.PID);
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.StatsRecord> KEY_STATS_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Stats.STATS, com.wbc.supervisor.database.generated.tables.Stats.STATS.SID);
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.ThresholdsRecord> KEY_THRESHOLDS_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Thresholds.THRESHOLDS, com.wbc.supervisor.database.generated.tables.Thresholds.THRESHOLDS.TID);
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.TopoinfoRecord> KEY_TOPOINFO_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO, com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.ID);
		public static final org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.VendormacsRecord> KEY_VENDORMACS_PRIMARY = createUniqueKey(com.wbc.supervisor.database.generated.tables.Vendormacs.VENDORMACS, com.wbc.supervisor.database.generated.tables.Vendormacs.VENDORMACS.CODE);
	}
}
