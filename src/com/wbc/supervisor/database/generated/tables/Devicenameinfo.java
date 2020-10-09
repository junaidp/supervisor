/**
 * This class is generated by jOOQ
 */
package com.wbc.supervisor.database.generated.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Devicenameinfo extends org.jooq.impl.TableImpl<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord> {

	private static final long serialVersionUID = 1902265737;

	/**
	 * The singleton instance of <code>dashboard2.devicenameinfo</code>
	 */
	public static final com.wbc.supervisor.database.generated.tables.Devicenameinfo DEVICENAMEINFO = new com.wbc.supervisor.database.generated.tables.Devicenameinfo();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord> getRecordType() {
		return com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord.class;
	}

	/**
	 * The column <code>dashboard2.devicenameinfo.deviceid</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, Integer> DEVICEID = createField("deviceid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.name</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.location</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> LOCATION = createField("location", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.ud1name</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> UD1NAME = createField("ud1name", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.ud2name</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> UD2NAME = createField("ud2name", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.ud3name</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> UD3NAME = createField("ud3name", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.ud4name</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> UD4NAME = createField("ud4name", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.ud5name</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> UD5NAME = createField("ud5name", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.ud6name</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> UD6NAME = createField("ud6name", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.vendorname</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> VENDORNAME = createField("vendorname", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.description</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>dashboard2.devicenameinfo.notes</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord, String> NOTES = createField("notes", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * Create a <code>dashboard2.devicenameinfo</code> table reference
	 */
	public Devicenameinfo() {
		this("devicenameinfo", null);
	}

	/**
	 * Create an aliased <code>dashboard2.devicenameinfo</code> table reference
	 */
	public Devicenameinfo(String alias) {
		this(alias, com.wbc.supervisor.database.generated.tables.Devicenameinfo.DEVICENAMEINFO);
	}

	private Devicenameinfo(String alias, org.jooq.Table<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Devicenameinfo(String alias, org.jooq.Table<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.wbc.supervisor.database.generated.Dashboard2.DASHBOARD2, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord> getPrimaryKey() {
		return com.wbc.supervisor.database.generated.Keys.KEY_DEVICENAMEINFO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord>>asList(com.wbc.supervisor.database.generated.Keys.KEY_DEVICENAMEINFO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.wbc.supervisor.database.generated.tables.Devicenameinfo as(String alias) {
		return new com.wbc.supervisor.database.generated.tables.Devicenameinfo(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.wbc.supervisor.database.generated.tables.Devicenameinfo rename(String name) {
		return new com.wbc.supervisor.database.generated.tables.Devicenameinfo(name, null);
	}
}